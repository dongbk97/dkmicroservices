package dev.ngdangkiet.service.impl;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.constant.ThresholdsConstant;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetAttendanceRecordsResponse;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthRequest;
import dev.ngdangkiet.dkmicroservices.attendance.protobuf.PGetTotalWorkingDayInMonthResponse;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.domain.AttendanceRecordEntity;
import dev.ngdangkiet.enums.attendance.AttendanceStatus;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.AttendanceRecordMapper;
import dev.ngdangkiet.repository.AttendanceRecordRepository;
import dev.ngdangkiet.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceRecordServiceImpl implements AttendanceRecordService {

    private final AttendanceRecordRepository attendanceRecordRepository;
    private final AttendanceRecordMapper attendanceRecordMapper = AttendanceRecordMapper.INSTANCE;

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
        double totalWorkingDay = 0;
        List<AttendanceRecordEntity> attendanceRecords = attendanceRecordRepository.findByEmployeeIdAndYearAndMonth(employeeId, year, month);
        for (AttendanceRecordEntity ar : attendanceRecords) {
            if (Objects.nonNull(ar.getWorkHours()) && !ar.getAttendanceDate().getDayOfWeek().equals(DayOfWeek.SATURDAY) && !ar.getAttendanceDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                if (ar.getWorkHours() < 6.5 && ar.getWorkHours() > 3.5) {
                    totalWorkingDay += 0.5;
                } else if (ar.getWorkHours() > 6.5) {
                    totalWorkingDay += 1;
                }
            }
        }
        return totalWorkingDay;
    }
}
