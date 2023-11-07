package dev.ngdangkiet.server;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginRequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.dkmicroservices.auth.service.AuthServiceGrpc;
import dev.ngdangkiet.service.AuthService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 11/5/2023
 */

@GrpcService
@RequiredArgsConstructor
public class AuthServiceGrpcServer extends AuthServiceGrpc.AuthServiceImplBase {

    private final AuthService authService;

    @Override
    public void authentication(PLoginRequest request, StreamObserver<PLoginResponse> responseObserver) {
        PLoginResponse response = authService.authentication(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
