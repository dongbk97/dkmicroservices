package dev.ngdangkiet.server;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsRequest;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsResponse;
import dev.ngdangkiet.dkmicroservices.notification.service.NotificationServiceGrpc;
import dev.ngdangkiet.service.NotificationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 11/17/2023
 */

@GrpcService
@RequiredArgsConstructor
public class NotificationServiceGrpcServer extends NotificationServiceGrpc.NotificationServiceImplBase {

    private final NotificationService notificationService;

    @Override
    public void getNotifications(PGetNotificationsRequest request, StreamObserver<PGetNotificationsResponse> responseObserver) {
        PGetNotificationsResponse response = notificationService.getNotifications(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void seenOrUnseenNotification(Int64Value request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = notificationService.seenOrUnseenNotification(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
