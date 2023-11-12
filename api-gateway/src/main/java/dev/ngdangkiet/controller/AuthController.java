package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.AuthGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.LoginRequestMapper;
import dev.ngdangkiet.mapper.response.LoginResponseMapper;
import dev.ngdangkiet.payload.request.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
