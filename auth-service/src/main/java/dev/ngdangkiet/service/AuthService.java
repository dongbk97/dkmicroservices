package dev.ngdangkiet.service;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginRequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

public interface AuthService {

    PLoginResponse authentication(PLoginRequest request);
}
