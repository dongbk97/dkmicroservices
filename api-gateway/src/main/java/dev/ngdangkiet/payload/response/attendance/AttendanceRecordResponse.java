package dev.ngdangkiet.payload.response.attendance;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Getter
@Setter
public class AttendanceRecordResponse {

    private Long id;
    private Long employeeId;
    private String attendanceDate;
    private String checkInTime;
    private String checkOutTime;
    private Double workHours;
    private String workTime;
    private String status;
}
