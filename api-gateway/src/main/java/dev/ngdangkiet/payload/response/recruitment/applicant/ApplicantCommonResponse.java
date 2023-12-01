package dev.ngdangkiet.payload.response.recruitment.applicant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@Getter
@Setter
public class ApplicantCommonResponse {

    private Long id;
    private String applicantName;
    private String email;
    private String phoneNumber;
    private String resumeUrl;
    private String applicationDate;
    private String status;
}
