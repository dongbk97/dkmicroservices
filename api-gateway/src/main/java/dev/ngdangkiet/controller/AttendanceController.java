package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.AttendanceGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PChangeStatusLeaveRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PHolidayRecord;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PListHolidays;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PSearchHolidaysRequest;
import dev.ngdangkiet.enums.tracking.Action;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.attendance.LeaveRequestMapper;
import dev.ngdangkiet.mapper.response.attendance.AttendanceRecordMapper;
import dev.ngdangkiet.mapper.response.attendance.HolidayRecordMapper;
import dev.ngdangkiet.payload.request.attendance.GetAttendanceRecordsRequest;
import dev.ngdangkiet.payload.request.attendance.GetTotalWorkingDayRequest;
import dev.ngdangkiet.payload.request.attendance.SubmitHolidaysRequest;
import dev.ngdangkiet.payload.request.attendance.SubmitLeaveRequest;
import dev.ngdangkiet.payload.response.attendance.TotalWorkingDayOfUserResponse;
import dev.ngdangkiet.redis.utils.CacheTrackingUtil;
import dev.ngdangkiet.security.SecurityHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ngdangkiet
 * @since 12/3/2023
 */

@Tag(name = "Attendance", description = "Attendance APIs")
@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceGrpcClient attendanceGrpcClient;
    private final CacheTrackingUtil cacheTrackingUtil;
    private final AttendanceRecordMapper attendanceRecordMapper = AttendanceRecordMapper.INSTANCE;
    private final LeaveRequestMapper leaveRequestMapper = LeaveRequestMapper.INSTANCE;
    private final HolidayRecordMapper holidayRecordMapper = HolidayRecordMapper.INSTANCE;

    @GetMapping("/check-in-out")
    public ApiMessage checkInOut() {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            var response = attendanceGrpcClient.checkInOut(userLogged.getUserInfo().getId());

            ApiMessage apiMessage = ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.SUCCESS : ApiMessage.FAILED;
            cacheTrackingUtil.cacheTrackingJson(Action.CHECK_IN_OUT.name(), userLogged.getUserInfo().getId(), apiMessage);

            return apiMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/records")
    public ApiMessage getAttendanceRecords(@ModelAttribute GetAttendanceRecordsRequest request) {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            var response = attendanceGrpcClient.getAttendanceRecords(
                    PGetAttendanceRecordsRequest.newBuilder()
                            .setEmployeeId(ObjectUtils.defaultIfNull(request.getEmployeeId(), userLogged.getUserInfo().getId()))
                            .setWeekOfMonth(ObjectUtils.defaultIfNull(request.getWeekOfMonth(), -1))
                            .setMonth(ObjectUtils.defaultIfNull(request.getMonth(), LocalDate.now().getMonthValue()))
                            .setYear(ObjectUtils.defaultIfNull(request.getYear(), LocalDate.now().getYear()))
                            .build()
            );
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.success(attendanceRecordMapper.toDomains(response.getDataList()))
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/total-working-days")
    public ApiMessage getTotalWorkingDay(@ModelAttribute GetTotalWorkingDayRequest request) {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            var response = attendanceGrpcClient.getTotalWorkingDayInMonth(
                    PGetTotalWorkingDayInMonthRequest.newBuilder()
                            .setEmployeeId(ObjectUtils.defaultIfNull(request.getEmployeeId(), userLogged.getUserInfo().getId()))
                            .setMonth(ObjectUtils.defaultIfNull(request.getMonth(), LocalDate.now().getMonthValue()))
                            .setYear(ObjectUtils.defaultIfNull(request.getYear(), LocalDate.now().getYear()))
                            .build()
            );
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.success(TotalWorkingDayOfUserResponse.builder()
                    .setTotalDayOfMonth(response.getData().getTotalDayOfMonth())
                    .setCurrentSystemTotalDayWorking(response.getData().getCurrentSystemTotalDayWorking())
                    .setCurrentUserTotalDayWorking(response.getData().getCurrentUserTotalDayWorking())
                    .build())
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PostMapping("/submit-leave-request")
    public ApiMessage submitLeaveRequest(@Valid @RequestBody SubmitLeaveRequest request) {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            request.setEmployeeId(userLogged.getUserInfo().getId());
            var response = attendanceGrpcClient.submitLeaveRequest(leaveRequestMapper.toProtobuf(request));
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.SUCCESS
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PatchMapping("/leave-request/{id}")
    public ApiMessage changeStatusLeaveRequest(@PathVariable Long id, @RequestParam(name = "status") String status) {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            var response = attendanceGrpcClient.changeStatusLeaveRequest(PChangeStatusLeaveRequest.newBuilder()
                    .setId(id)
                    .setEmployeeId(userLogged.getUserInfo().getId())
                    .setStatus(status)
                    .build());
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.SUCCESS
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PostMapping("/submit-holidays")
    public ApiMessage changeStatusLeaveRequest(@RequestBody List<SubmitHolidaysRequest> request) {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            var response = attendanceGrpcClient.submitHolidays(
                    PListHolidays.newBuilder()
                            .addAllData(request.stream()
                                    .map(r -> PHolidayRecord.newBuilder()
                                            .setName(r.getName())
                                            .setDateYear(r.getYear())
                                            .setType(r.getType())
                                            .setDate(r.getDate())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build()
            );
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.SUCCESS
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/search-holidays")
    public ApiMessage getHolidaysByConditions(@RequestParam(required = false) String dateFrom,
                                              @RequestParam(required = false) String dateTo,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Integer year,
                                              @RequestParam(required = false) Integer month) {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            var response = attendanceGrpcClient.getHolidaysByConditions(
                    PSearchHolidaysRequest.newBuilder()
                            .setDateFrom(ObjectUtils.defaultIfNull(dateFrom, ""))
                            .setDateTo(ObjectUtils.defaultIfNull(dateTo, ""))
                            .setName(ObjectUtils.defaultIfNull(name, ""))
                            .setType(ObjectUtils.defaultIfNull(type, ""))
                            .setYear(ObjectUtils.defaultIfNull(year, LocalDate.now().getYear()))
                            .setMonth(ObjectUtils.defaultIfNull(month, LocalDate.now().getMonthValue()))
                            .build()
            );
            return ErrorHelper.isSuccess(response.getCode())
                    ? ApiMessage.success(holidayRecordMapper.toDomains(response.getDataList()))
                    : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
