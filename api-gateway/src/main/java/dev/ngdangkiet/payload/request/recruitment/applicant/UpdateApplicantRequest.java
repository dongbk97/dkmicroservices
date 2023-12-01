package dev.ngdangkiet.payload.request.recruitment.applicant;

import dev.ngdangkiet.validation.ValidationApplicantStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Getter
@Setter
public class UpdateApplicantRequest extends UpsertApplicantRequest {

    @NotNull(message = "Id shouldn't be null or empty!")
    private Long id;

    @ValidationApplicantStatus
    private String status;
}
