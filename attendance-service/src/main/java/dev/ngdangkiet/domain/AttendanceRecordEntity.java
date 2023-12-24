package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.attendance.AttendanceStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_attendance_record")
public class AttendanceRecordEntity extends BaseEntity {

    private Long employeeId;

    private LocalDate attendanceDate;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    private Double workHours;

    private String workTime;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
