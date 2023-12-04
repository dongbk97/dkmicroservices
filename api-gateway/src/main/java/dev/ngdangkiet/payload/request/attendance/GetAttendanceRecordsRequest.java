package dev.ngdangkiet.payload.request.attendance;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Getter
@Setter
public class GetAttendanceRecordsRequest implements Serializable {

    private Long employeeId;
    private Integer weekOfMonth;
    private Integer month;
    private Integer year;
}
