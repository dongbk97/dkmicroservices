package dev.ngdangkiet.service;

import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PChangePasswordRequest;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

public interface UserService {

    EmptyResponse changePassword(PChangePasswordRequest request);
}
