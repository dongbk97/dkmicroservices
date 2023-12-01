package dev.ngdangkiet.payload.response.recruitment.jobposting;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/28/2023
 */

@Getter
@Setter
public class JobPostingResponse {

    private Long id;
    private String jobTitle;
    private String jobDescription;
    private String postedDate;
    private String applicationDeadline;
    private String status;
}
