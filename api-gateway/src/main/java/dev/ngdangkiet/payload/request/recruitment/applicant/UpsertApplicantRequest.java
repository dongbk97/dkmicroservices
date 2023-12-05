package dev.ngdangkiet.payload.request.recruitment.applicant;

import dev.ngdangkiet.validation.annotation.ValidationPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/30/2023
 */

@Getter
@Setter
public class UpsertApplicantRequest implements Serializable {

    @NotNull(message = "Job posting shouldn't be null or empty!")
    private JobPosting jobPosting;

    @NotBlank(message = "Applicant name shouldn't be null or empty!")
    private String applicantName;

    @Email(message = "Invalid email!")
    private String email;

    @NotBlank(message = "Phone number shouldn't be null or empty!")
    @ValidationPhoneNumber
    private String phoneNumber;

    @NotBlank(message = "Resume url shouldn't be null or empty!")
    private String resumeUrl;

    @Getter
    @Setter
    public static class JobPosting {

        @NotNull(message = "Job posting ID shouldn't be null or empty!")
        private Long id;
    }
}
