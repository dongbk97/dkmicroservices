package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PGenerateQRCodeResponse;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginRequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PRefreshTokenResponse;
import dev.ngdangkiet.dkmicroservices.auth.service.AuthServiceGrpc;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.service.AuthService;
import dev.ngdangkiet.service.MailService;
import dev.ngdangkiet.service.RefreshTokenService;
import dev.ngdangkiet.service.TwoFactorAuthService;
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
    private final RefreshTokenService refreshTokenService;
    private final MailService mailService;
    private final TwoFactorAuthService twoFactorAuthService;

    @Override
    public void authentication(PLoginRequest request, StreamObserver<PLoginResponse> responseObserver) {
        PLoginResponse response = authService.authentication(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void refreshToken(StringValue tokenUUID, StreamObserver<PRefreshTokenResponse> responseObserver) {
        PRefreshTokenResponse response = refreshTokenService.refreshToken(tokenUUID);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void sendMailOtp(StringValue request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = mailService.sendMailWithOtp(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void enable2FA(Int64Value employeeId, StreamObserver<PGenerateQRCodeResponse> responseObserver) {
        PGenerateQRCodeResponse response = twoFactorAuthService.enable2FA(employeeId);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
