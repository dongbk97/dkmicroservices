package dev.ngdangkiet.service;

import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PRefreshTokenResponse;

/**
 * @author ngdangkiet
 * @since 11/22/2023
 */

public interface RefreshTokenService {

    String createAndGetRefreshToken(Long userId);

    PRefreshTokenResponse refreshToken(StringValue request);
}
