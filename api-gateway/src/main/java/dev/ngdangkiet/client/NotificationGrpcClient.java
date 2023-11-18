package dev.ngdangkiet.client;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsRequest;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsResponse;
import dev.ngdangkiet.dkmicroservices.notification.service.NotificationServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/17/2023
 */

@Component
@RequiredArgsConstructor
public class NotificationGrpcClient {

    @GrpcClient("grpc-notification-service")
    private final NotificationServiceGrpc.NotificationServiceBlockingStub notificationServiceBlockingStub;

    public PGetNotificationsResponse getNotifications(PGetNotificationsRequest request) {
        return notificationServiceBlockingStub.getNotifications(request);
    }

    public EmptyResponse seenOrUnseenNotification(Int64Value request) {
        return notificationServiceBlockingStub.seenOrUnseenNotification(request);
    }
}
