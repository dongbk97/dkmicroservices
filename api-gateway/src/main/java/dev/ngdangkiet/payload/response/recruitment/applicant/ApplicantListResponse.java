package dev.ngdangkiet.payload.response.recruitment.applicant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@Getter
@Setter
public class ApplicantListResponse extends ApplicantCommonResponse {

    private Long jobPostingId;
}
