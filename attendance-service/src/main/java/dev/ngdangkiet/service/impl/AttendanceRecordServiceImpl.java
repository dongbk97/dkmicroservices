package dev.ngdangkiet.service.impl;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.constant.ThresholdsConstant;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.domain.AttendanceRecordEntity;
import dev.ngdangkiet.enums.attendance.AttendanceStatus;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.repository.AttendanceRecordRepository;
import dev.ngdangkiet.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
                attendanceRecord.setWorkHours(duration.toHours() + (Math.floor(((double) duration.toMinutes() / 60) * 100) / 100));
                attendanceRecord.setWorkTime(String.format("%s:%s", duration.toHours(), duration.toMinutes()));
            }

            // TODO: Save changes
            attendanceRecordRepository.save(attendanceRecord);
            builder.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
