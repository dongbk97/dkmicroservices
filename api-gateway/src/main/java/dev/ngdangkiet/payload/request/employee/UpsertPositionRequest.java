package dev.ngdangkiet.payload.request.employee;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/26/2023
 */

@Getter
@Setter
public class UpsertPositionRequest implements Serializable {

    private Long id;
    @NotBlank(message = "Position name shouldn't be null or empty!")
    private String name;
}
