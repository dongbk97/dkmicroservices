package dev.ngdangkiet.client;

import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginRequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PRefreshTokenResponse;
import dev.ngdangkiet.dkmicroservices.auth.service.AuthServiceGrpc;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Component
@RequiredArgsConstructor
public class AuthGrpcClient {

    @GrpcClient("grpc-auth-service")
    private final AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public PLoginResponse authentication(PLoginRequest request) {
        return authServiceBlockingStub.authentication(request);
    }

    public PRefreshTokenResponse refreshToken(StringValue tokenUUID) {
        return authServiceBlockingStub.refreshToken(tokenUUID);
    }

    public EmptyResponse sendMailOtp(StringValue mailTo) {
        return authServiceBlockingStub.sendMailOtp(mailTo);
    }
}
