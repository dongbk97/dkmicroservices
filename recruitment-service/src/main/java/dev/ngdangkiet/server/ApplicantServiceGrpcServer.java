package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicant;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PApplicantResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetApplicantsRequest;
import dev.ngdangkiet.dkmicroservices.recruitment.protobuf.PGetApplicantsResponse;
import dev.ngdangkiet.dkmicroservices.recruitment.service.ApplicantServiceGrpc;
import dev.ngdangkiet.service.ApplicantService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 11/29/2023
 */

@GrpcService
@RequiredArgsConstructor
public class ApplicantServiceGrpcServer extends ApplicantServiceGrpc.ApplicantServiceImplBase {

    private final ApplicantService applicantService;

    @Override
    public void upsertApplicant(PApplicant request, StreamObserver<Int64Value> responseObserver) {
        Int64Value response = applicantService.upsertApplicant(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getApplicantById(Int64Value request, StreamObserver<PApplicantResponse> responseObserver) {
        PApplicantResponse response = applicantService.getApplicantById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getApplicants(PGetApplicantsRequest request, StreamObserver<PGetApplicantsResponse> responseObserver) {
        PGetApplicantsResponse response = applicantService.getApplicants(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteApplicantById(Int64Value request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = applicantService.deleteApplicantById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
