package dev.ngdangkiet.payload.request.payroll;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 12/17/2023
 */

@Getter
@Setter
public class UpsertTaxRequest implements Serializable {

    private Long id;
    @NotNull(message = "EmployeeID shouldn't be not null or empty!")
    private Long employeeId;
    @NotBlank(message = "Tax type shouldn't be not null or empty!")
    private String taxType;
    @NotNull(message = "Rate shouldn't be not null or empty!")
    private Double rate;
    @NotBlank(message = "Effective date shouldn't be not null or empty!")
    private String effectiveDate;
}
