package dev.ngdangkiet.payload.response.recruitment.applicant;

import dev.ngdangkiet.payload.response.recruitment.jobposting.JobPostingResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@Getter
@Setter
public class ApplicantDetailResponse extends ApplicantCommonResponse {

    private JobPostingResponse jobPosting;
}
