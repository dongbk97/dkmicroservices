package dev.ngdangkiet.service.impl;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.constant.ThresholdsConstant;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PChangeStatusLeaveRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsResponse;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthResponse;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PLeaveRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PListHolidays;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PSearchHolidaysRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PSearchHolidaysResponse;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.domain.AttendanceRecordEntity;
import dev.ngdangkiet.domain.DTO.SearchHolidayDTO;
import dev.ngdangkiet.domain.HolidayEntity;
import dev.ngdangkiet.domain.LeaveRequestEntity;
import dev.ngdangkiet.enums.attendance.AttendanceStatus;
import dev.ngdangkiet.enums.attendance.HolidayType;
import dev.ngdangkiet.enums.attendance.LeaveRequestStatus;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.AttendanceRecordMapper;
import dev.ngdangkiet.mapper.HolidayMapper;
import dev.ngdangkiet.mapper.LeaveRequestMapper;
import dev.ngdangkiet.mapper.SearchHolidayMapper;
import dev.ngdangkiet.rabbitmq.RabbitMQProducer;
import dev.ngdangkiet.repository.AttendanceRecordRepository;
import dev.ngdangkiet.repository.HolidayRepository;
import dev.ngdangkiet.repository.HolidayRepositoryCustom;
import dev.ngdangkiet.repository.LeaveRequestRepository;
import dev.ngdangkiet.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceRecordServiceImpl implements AttendanceRecordService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final HolidayRepositoryCustom holidayRepositoryCustom;
    private final AttendanceRecordMapper attendanceRecordMapper = AttendanceRecordMapper.INSTANCE;
    private final LeaveRequestMapper leaveRequestMapper = LeaveRequestMapper.INSTANCE;
    private final HolidayMapper holidayMapper = HolidayMapper.INSTANCE;
    private final SearchHolidayMapper searchHolidayMapper = SearchHolidayMapper.INSTANCE;
    private final HolidayRepository holidayRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Override
    public EmptyResponse checkInOut(Int64Value employeeId) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            // TODO: Check exist checkin of employee in current date, exist ? updateCheckout : checkin
            AttendanceRecordEntity attendanceRecord = attendanceRecordRepository.findByEmployeeIdAndAttendanceDate(employeeId.getValue(), LocalDate.now())
                    .orElse(null);

            if (Objects.isNull(attendanceRecord)) {
                attendanceRecord = AttendanceRecordEntity.builder()
                        .setEmployeeId(employeeId.getValue())
                        .setAttendanceDate(LocalDate.now())
                        .setCheckInTime(LocalTime.now())
                        .setStatus(AttendanceStatus.PRESENT)
                        .build();

                // TODO: Check attendance status
                if (attendanceRecord.getCheckInTime().isAfter(LocalTime.of(ThresholdsConstant.SCHEDULED_START_TIME + ThresholdsConstant.GRACE_PERIOD_LATE_CHECKIN, 0))) {
                    attendanceRecord.setStatus(AttendanceStatus.LATE);
                }
            } else {
                attendanceRecord.setCheckOutTime(LocalTime.now());
                if (attendanceRecord.getCheckOutTime().isBefore(LocalTime.of(ThresholdsConstant.SCHEDULED_END_TIME - ThresholdsConstant.GRACE_PERIOD_EARLY_CHECKOUT, 0))) {
                    attendanceRecord.setStatus(AttendanceStatus.EARLY_DEPARTURE);
                }
                Duration duration = Duration.between(attendanceRecord.getCheckInTime(), attendanceRecord.getCheckOutTime());

                long hours = attendanceRecord.getCheckOutTime().isAfter(LocalTime.of(13, 0))
                        ? duration.toHours() - 1
                        : duration.toHours();

                attendanceRecord.setWorkHours(hours + (Math.floor(((double) duration.toMinutesPart() / 60) * 100) / 100));
                attendanceRecord.setWorkTime(String.format("%s:%s", hours, duration.toMinutesPart()));
            }

            // TODO: Save changes
            attendanceRecordRepository.save(attendanceRecord);
            builder.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public PGetAttendanceRecordsResponse getAttendanceRecords(PGetAttendanceRecordsRequest request) {
        PGetAttendanceRecordsResponse.Builder builder = PGetAttendanceRecordsResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            // TODO: get attendance records by request
            List<AttendanceRecordEntity> attendanceRecords = attendanceRecordRepository.findByEmployeeIdAndYearAndMonth(
                    request.getEmployeeId(),
                    request.getYear(),
                    request.getMonth());

            builder.setCode(ErrorCode.SUCCESS);
            // TODO: extract by week request
            if (request.getWeekOfMonth() > 0) {
                List<AttendanceRecordEntity> filteredAttendanceRecords = attendanceRecords.stream()
                        .filter(ar -> ar.getAttendanceDate().get(WeekFields.of(Locale.getDefault()).weekOfMonth()) == request.getWeekOfMonth())
                        .toList();
                builder.addAllData(attendanceRecordMapper.toProtobufs(filteredAttendanceRecords));
            } else {
                builder.addAllData(attendanceRecordMapper.toProtobufs(attendanceRecords));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public PGetTotalWorkingDayInMonthResponse getTotalWorkingDayInMonth(PGetTotalWorkingDayInMonthRequest request) {
        PGetTotalWorkingDayInMonthResponse.Builder builder = PGetTotalWorkingDayInMonthResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            // TODO: Get total day in month exclude Sar and Sun
            YearMonth yearMonth = YearMonth.of(request.getYear(), request.getMonth());
            log.info("Total day in month [{}] of year [{}]: [{}]", request.getMonth(), request.getYear(), yearMonth.lengthOfMonth());
            int totalDayInMonthExcludeSarAndSun = totalDayExcludeSarAndSun(yearMonth.getYear(), yearMonth.getMonthValue(), yearMonth.lengthOfMonth());
            log.info("Total day in month [{}] of year [{}] exclude Sar and Sun: [{}]", request.getMonth(), request.getYear(), totalDayInMonthExcludeSarAndSun);

            // TODO: Get current system and user total working day
            LocalDate localDate = LocalDate.now();
            double currentSystemTotalWorkingDay = request.getMonth() == localDate.getMonthValue() && request.getYear() == localDate.getYear()
                    ? totalDayExcludeSarAndSun(request.getYear(), request.getMonth(), localDate.getDayOfMonth())
                    : totalDayInMonthExcludeSarAndSun;

            double currentUserTotalWorkingDay = totalWorkingDayOfUser(request.getEmployeeId(), request.getYear(), request.getMonth());

            // TODO: Response data
            builder.setCode(ErrorCode.SUCCESS).setData(PGetTotalWorkingDayInMonthResponse.Data.newBuilder()
                    .setTotalDayOfMonth(totalDayInMonthExcludeSarAndSun)
                    .setCurrentSystemTotalDayWorking(currentSystemTotalWorkingDay)
                    .setCurrentUserTotalDayWorking(currentUserTotalWorkingDay)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse submitLeaveRequest(PLeaveRequest request) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            LeaveRequestEntity leaveRequest;
            if (request.getId() > 0) {
                leaveRequest = leaveRequestRepository.findById(request.getId()).orElse(null);
                if (Objects.isNull(leaveRequest)) {
                    log.error("LeaveRequest [{}] not found!", request.getId());
                    return builder.setCode(ErrorCode.NOT_FOUND).build();
                }
            }

            leaveRequest = leaveRequestMapper.toDomain(request);
            leaveRequestRepository.save(leaveRequest);
            rabbitMQProducer.sendNewLeaveRequestNotification(leaveRequest);

            builder.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse changeStatusLeaveRequest(PChangeStatusLeaveRequest request) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            if (!LeaveRequestStatus.isValidLeaveRequestStatus(request.getStatus())) {
                log.error("Invalid leave request status!");
                return builder.setCode(ErrorCode.INVALID_DATA).build();
            }

            // TODO: Get leave request for update
            LeaveRequestStatus status = LeaveRequestStatus.of(request.getStatus());
            boolean isCancelLeaveRequest = LeaveRequestStatus.CANCELED.equals(status);
            LeaveRequestEntity leaveRequest = isCancelLeaveRequest
                    ? leaveRequestRepository.findByIdAndEmployeeId(request.getId(), request.getEmployeeId()).orElse(null)
                    : leaveRequestRepository.findByIdAndReceiverId(request.getId(), request.getEmployeeId()).orElse(null);

            if (Objects.isNull(leaveRequest)) {
                log.error("LeaveRequest ID [{}] not found!", request.getId());
                return builder.setCode(ErrorCode.NOT_FOUND).build();
            }

            leaveRequest.setStatus(status);
            leaveRequestRepository.save(leaveRequest);

            // TODO: Send notification to employee and update attendance record
            if (!isCancelLeaveRequest) {
                if (LeaveRequestStatus.APPROVED.equals(status)) {
                    List<LocalDate> datesLeave = leaveRequest.getStartDate().datesUntil(leaveRequest.getEndDate()).toList();
                    List<AttendanceRecordEntity> upsertAttendanceRecord = new ArrayList<>();

                    datesLeave.forEach(dl -> {
                        AttendanceRecordEntity attendanceRecord = attendanceRecordRepository.findByEmployeeIdAndAttendanceDate(request.getEmployeeId(), dl).orElseGet(() ->
                                AttendanceRecordEntity.builder()
                                        .setEmployeeId(request.getEmployeeId())
                                        .setAttendanceDate(dl)
                                        .build());
                        attendanceRecord.setStatus(AttendanceStatus.ON_LEAVE);
                        upsertAttendanceRecord.add(attendanceRecord);
                    });

                    attendanceRecordRepository.saveAll(upsertAttendanceRecord);
                }
                rabbitMQProducer.sendNewUpdateLeaveRequestNotification(leaveRequest);
            }

            builder.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse submitHolidays(PListHolidays requestList) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            List<HolidayEntity> holidayEntities = holidayMapper.toDomains(requestList.getDataList());
            if (!CollectionUtils.isEmpty(holidayEntities)) {
                // validate duplicate holiday
                if (validateHolidaysExisted(holidayEntities)) {
                    return builder.setCode(ErrorCode.ALREADY_EXISTS).build();
                }
                holidayEntities.forEach(this::processHolidayEntity);
                holidayRepository.saveAll(holidayEntities);
                builder.setCode(ErrorCode.SUCCESS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public PSearchHolidaysResponse getHolidaysByConditions(PSearchHolidaysRequest search) {
        PSearchHolidaysResponse.Builder builder = PSearchHolidaysResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            SearchHolidayDTO searchHolidayDTO = searchHolidayMapper.toDomain(search);
            List<HolidayEntity> holidayEntities = holidayRepositoryCustom.findByConditions(searchHolidayDTO);
            return builder.setCode(ErrorCode.SUCCESS)
                    .addAllData(holidayMapper.toProtobufs(holidayEntities))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Could not fond Holidays with error : " + e.getMessage());
            return builder.build();
        }
    }

    private int totalDayExcludeSarAndSun(int year, int month, int days) {
        int totalDayInMonthExcludeSarAndSun = 0;
        for (int i = 1; i <= days; i++) {
            LocalDate localDate = LocalDate.of(year, month, i);
            if (!localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                totalDayInMonthExcludeSarAndSun++;
            }
        }
        return totalDayInMonthExcludeSarAndSun;
    }

    private double totalWorkingDayOfUser(Long employeeId, int year, int month) {
        List<AttendanceRecordEntity> attendanceRecords = attendanceRecordRepository.findByEmployeeIdAndYearAndMonth(employeeId, year, month);
        return attendanceRecords.stream()
                .filter(ar -> !Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(ar.getAttendanceDate().getDayOfWeek()))
                .mapToDouble(ar -> {
                    if (AttendanceStatus.ON_LEAVE.equals(ar.getStatus())) {
                        return 1;
                    } else if (Objects.nonNull(ar.getWorkHours())) {
                        if (ar.getWorkHours() < 6.5 && ar.getWorkHours() > 3.5) {
                            return 0.5;
                        } else if (ar.getWorkHours() > 6.5) {
                            return 1;
                        }
                    }
                    return 0;
                })
                .sum();
    }

    private void processHolidayEntity(HolidayEntity holiday) {
        LocalDate date = holiday.getDate();
        holiday.setDate_year(date.getYear());
        holiday.setDate_month(date.getMonthValue());
        holiday.setWeek_day(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        holiday.setDate_day(date.getDayOfMonth());

        //set Default value for type of Holiday
        if (!StringUtils.hasText(holiday.getType())) {
            holiday.setType(HolidayType.INTERNAL.toString());
        }
    }

    private Boolean validateHolidaysExisted(List<HolidayEntity> holidayEntities) {
        Set<LocalDate> dateToValidate = holidayEntities.stream().map(HolidayEntity::getDate).collect(Collectors.toSet());
        List<HolidayEntity> holidaysExited = holidayRepository.findByDates(dateToValidate);
        return CollectionUtils.isEmpty(holidaysExited) ? Boolean.FALSE : Boolean.TRUE;
    }
}
