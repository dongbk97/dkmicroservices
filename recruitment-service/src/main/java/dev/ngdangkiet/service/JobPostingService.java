package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetJobPostingsRequest;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPosting;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPostingResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPostingsResponse;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

public interface JobPostingService {

    Int64Value upsertJobPosting(PJobPosting request);

    PJobPostingsResponse getJobPostings(PGetJobPostingsRequest request);

    PJobPostingResponse getJobPostingById(Int64Value request);

    EmptyResponse deleteJobPostingById(Int64Value request);
}
