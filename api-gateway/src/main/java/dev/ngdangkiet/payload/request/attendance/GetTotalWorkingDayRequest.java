package dev.ngdangkiet.payload.request.attendance;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Getter
@Setter
public class GetTotalWorkingDayRequest implements Serializable {

    private Long employeeId;
    private Integer month;
    private Integer year;
}
