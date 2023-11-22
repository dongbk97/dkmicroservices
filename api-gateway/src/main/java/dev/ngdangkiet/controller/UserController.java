package dev.ngdangkiet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ngdangkiet.client.UserGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.constant.RedisCacheKeyConstant;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PChangePasswordRequest;
import dev.ngdangkiet.domain.tracking.TrackingJson;
import dev.ngdangkiet.enums.Action;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.payload.request.ChangePasswordRequest;
import dev.ngdangkiet.redis.RedisConfig;
import dev.ngdangkiet.security.SecurityHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

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
    private final RedisConfig redisConfig;
    private final ObjectMapper objectMapper;

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

            ApiMessage apiMessage = ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.SUCCESS : ApiMessage.UPDATE_FAILED;
            redisConfig.put(String.format(RedisCacheKeyConstant.Tracking.USER_TRACKING_KEY, userLogged.getToken(), userLogged.getUserInfo().getId()),
                    TrackingJson.builder()
                            .setAction(Action.CHANGE_PASSWORD.name())
                            .setRequestBodyJson(objectMapper.writeValueAsString(request))
                            .setResponseBodyJson(objectMapper.writeValueAsString(apiMessage))
                            .build(),
                    Duration.ofMinutes(2));

            return apiMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
