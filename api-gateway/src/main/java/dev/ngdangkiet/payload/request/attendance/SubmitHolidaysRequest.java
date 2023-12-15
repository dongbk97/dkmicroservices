package dev.ngdangkiet.payload.request.attendance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SubmitHolidaysRequest implements Serializable {

    @NotNull(message = "Year shouldn't be null or empty!")
    private Integer year;

    @NotBlank(message = "Holiday's Date shouldn't be null or empty!")
    private String date;

    @NotBlank(message = "Holiday's Name shouldn't be null or empty!")
    private String name;

    private String type;
}
