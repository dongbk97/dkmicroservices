package dev.ngdangkiet.payload.request.payroll;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 12/17/2023
 */

@Getter
@Setter
public class GetPayrollsRequest implements Serializable {

    private Long employeeId;
    private Integer month;
    private Integer year;
}
