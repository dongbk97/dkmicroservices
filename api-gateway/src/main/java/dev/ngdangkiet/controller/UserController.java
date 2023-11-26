package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.UserGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PChangePasswordRequest;
import dev.ngdangkiet.enums.Action;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.payload.request.user.ChangePasswordRequest;
import dev.ngdangkiet.redis.utils.CacheTrackingUtil;
import dev.ngdangkiet.security.SecurityHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserGrpcClient userGrpcClient;
    private final CacheTrackingUtil cacheTrackingUtil;

    @PutMapping("/change-password")
    public ApiMessage changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;

        try {
            var response = userGrpcClient.changePassword(
                    PChangePasswordRequest.newBuilder()
                            .setUserId(userLogged.getUserInfo().getId())
                            .setCurrentPassword(request.getCurrentPassword())
                            .setNewPassword(request.getNewPassword())
                            .build()
            );

            ApiMessage apiMessage = ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.SUCCESS : ApiMessage.failed(response.getCode());
            cacheTrackingUtil.cacheTrackingJson(Action.CHANGE_PASSWORD.name(), request, apiMessage);

            return apiMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
