package dev.ngdangkiet.payload.request.employee;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

@Getter
@Setter
public class UpdateEmployeeRequest extends UpsertEmployeeRequest {

    @NotNull(message = "Id shouldn't be null or empty!")
    private Long id;
}
