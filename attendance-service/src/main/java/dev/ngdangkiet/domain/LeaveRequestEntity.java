package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.attendance.LeaveRequestStatus;
import dev.ngdangkiet.enums.attendance.LeaveType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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
@ToString
@Table(name = "tbl_leave_request")
public class LeaveRequestEntity extends BaseEntity {

    private Long employeeId;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveRequestStatus status;

    private String reason;

    private Long receiverId;
}
