package dev.ngdangkiet.service;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PEnableOrDisable2FARequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PGenerateQRCodeResponse;

/**
 * @author ngdangkiet
 * @since 12/20/2023
 */

public interface TwoFactorAuthService {

    PGenerateQRCodeResponse enableOrDisable2FA(PEnableOrDisable2FARequest employeeId);
}
