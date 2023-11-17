package dev.ngdangkiet.controller;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.client.NotificationGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsRequest;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.response.NotificationResponseMapper;
import dev.ngdangkiet.security.SecurityHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

@Tag(name = "Notification", description = "Notification APIs")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationGrpcClient notificationGrpcClient;
    private final NotificationResponseMapper notificationResponseMapper = NotificationResponseMapper.INSTANCE;

    @GetMapping("/my")
    public ApiMessage getNotifications(@RequestParam(name = "all", required = false) Boolean all,
                                       @RequestParam(name = "seen", required = false) Boolean seen) {
        try {
            var userLogged = SecurityHelper.getUserLogin();
            assert userLogged != null;
            var response = notificationGrpcClient.getNotifications(PGetNotificationsRequest.newBuilder()
                    .setReceiverId(userLogged.getUserInfo().getId())
                    .setAll(ObjectUtils.allNull(all, seen) ? Boolean.TRUE : all)
                    .setSeen(Boolean.TRUE.equals(seen))
                    .build());

            return ApiMessage.success(notificationResponseMapper.toDomains(response.getNotificationsList()));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/seen/{id}")
    public ApiMessage seenOrUnseenNotification(@PathVariable(value = "id") Long notificationId) {
        try {
            var response = notificationGrpcClient.seenOrUnseenNotification(Int64Value.of(notificationId));
            return ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.SUCCESS : ApiMessage.FAILED;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
