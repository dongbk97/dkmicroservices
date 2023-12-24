package dev.ngdangkiet.controller;

import com.google.protobuf.StringValue;
import dev.ngdangkiet.client.AuthGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PEnableOrDisable2FARequest;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.LoginRequestMapper;
import dev.ngdangkiet.mapper.response.LoginResponseMapper;
import dev.ngdangkiet.payload.request.auth.LoginRequest;
import dev.ngdangkiet.payload.response.auth.RefreshTokenResponse;
import dev.ngdangkiet.ratelimit.RateLimitProperties;
import dev.ngdangkiet.ratelimit.RefreshTokenCounter;
import dev.ngdangkiet.security.SecurityHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Tag(name = "Authentication", description = "Authentication APIs")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthGrpcClient authGrpcClient;
    private final RefreshTokenCounter refreshTokenCounter;
    private final RateLimitProperties rateLimitProperties;
    private final LoginRequestMapper loginRequestMapper = LoginRequestMapper.INSTANCE;
    private final LoginResponseMapper loginResponseMapper = LoginResponseMapper.INSTANCE;

    @Operation(summary = "Login by email & password")
    @PostMapping("/login")
    public ApiMessage authentication(@RequestBody LoginRequest request) {
        try {
            var data = authGrpcClient.authentication(loginRequestMapper.toProtobuf(request));
            if (ErrorHelper.isFailed(data.getCode())) {
                return ApiMessage.LOGIN_FAILED;
            }
            return ApiMessage.success(loginResponseMapper.toDomain(data));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @Operation(summary = "Refresh token by tokenUUID")
    @GetMapping("/refresh-token")
    public ApiMessage refreshToken(@RequestParam(name = "tokenUUID") String tokenUUID) {
        try {
            if (refreshTokenCounter.getCounter() < rateLimitProperties.getLimit()) {
                refreshTokenCounter.increment();
                var data = authGrpcClient.refreshToken(StringValue.of(tokenUUID));
                if (ErrorHelper.isFailed(data.getCode())) {
                    return ApiMessage.FAILED;
                }
                return ApiMessage.success(RefreshTokenResponse.builder()
                        .setToken(data.getToken())
                        .build());
            } else {
                return ApiMessage.TOO_MANY_REQUESTS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @Operation(summary = "Send mail for receive OTP")
    @GetMapping("/send-mail-otp")
    public ApiMessage sendMailOtp(@RequestParam(name = "mailTo") String mailTo) {
        try {
            var response = authGrpcClient.sendMailOtp(StringValue.of(mailTo));
            return ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.SUCCESS : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/mode-2FA")
    public ApiMessage enableOrDisable2FA(@RequestParam(name = "enable") Boolean enable) {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;
        try {
            var response = authGrpcClient.enableOrDisable2FA(PEnableOrDisable2FARequest.newBuilder()
                    .setUserId(userLogged.getUserInfo().getId())
                    .setEnable(Boolean.TRUE.equals(enable))
                    .build());
            return ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.success(response.getQrCodeImageUrl()) : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
