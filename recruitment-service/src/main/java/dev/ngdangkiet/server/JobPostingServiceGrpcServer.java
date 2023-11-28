package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetJobPostingsRequest;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPosting;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPostingResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PJobPostingsResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.service.JobPostingServiceGrpc;
import dev.ngdangkiet.service.JobPostingService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

@GrpcService
@RequiredArgsConstructor
public class JobPostingServiceGrpcServer extends JobPostingServiceGrpc.JobPostingServiceImplBase {

    private final JobPostingService jobPostingService;

    @Override
    public void upsertJobPosting(PJobPosting request, StreamObserver<Int64Value> responseObserver) {
        Int64Value response = jobPostingService.upsertJobPosting(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getJobPostings(PGetJobPostingsRequest request, StreamObserver<PJobPostingsResponse> responseObserver) {
        PJobPostingsResponse response = jobPostingService.getJobPostings(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getJobPostingById(Int64Value request, StreamObserver<PJobPostingResponse> responseObserver) {
        PJobPostingResponse response = jobPostingService.getJobPostingById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteJobPostingById(Int64Value request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = jobPostingService.deleteJobPostingById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
