package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PGenerateQRCodeResponse;

/**
 * @author ngdangkiet
 * @since 12/20/2023
 */

public interface TwoFactorAuthService {

    PGenerateQRCodeResponse enable2FA(Int64Value employeeId);
}
