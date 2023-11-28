package dev.ngdangkiet.client;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetJobPostingsRequest;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPosting;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPostingResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPostingsResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.service.JobPostingServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/28/2023
 */

@Component
@RequiredArgsConstructor
public class RecruitmentGrpcClient {

    @GrpcClient("grpc-recruitment-service")
    private final JobPostingServiceGrpc.JobPostingServiceBlockingStub jobPostingServiceBlockingStub;

    public long upsertJobPosting(PJobPosting request) {
        return jobPostingServiceBlockingStub.upsertJobPosting(request).getValue();
    }

    public PJobPostingsResponse getJobPostings(PGetJobPostingsRequest request) {
        return jobPostingServiceBlockingStub.getJobPostings(request);
    }

    public PJobPostingResponse getJobPostingById(Long jobPostingId) {
        return jobPostingServiceBlockingStub.getJobPostingById(Int64Value.of(jobPostingId));
    }

    public EmptyResponse deleteJobPostingById(Long jobPostingId) {
        return jobPostingServiceBlockingStub.deleteJobPostingById(Int64Value.of(jobPostingId));
    }
}
