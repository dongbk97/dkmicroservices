package dev.ngdangkiet.payload.request.recruitment.jobposting;

import dev.ngdangkiet.validation.annotation.ValidationDeadline;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/28/2023
 */

@Getter
@Setter
public class UpsertJobPostingRequest implements Serializable {

    @NotBlank(message = "Job title shouldn't be null or empty!")
    private String jobTitle;

    @NotBlank(message = "Job description shouldn't be null or empty!")
    private String jobDescription;

    @ValidationDeadline
    private String applicationDeadline;
}
