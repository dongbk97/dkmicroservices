package dev.ngdangkiet.client;

import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesRequest;
import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesResponse;
import dev.ngdangkiet.dkmicroservices.tracking.service.TrackingServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

@Component
@RequiredArgsConstructor
public class TrackingGrpcClient {

    @GrpcClient("grpc-tracking-service")
    private final TrackingServiceGrpc.TrackingServiceBlockingStub trackingServiceBlockingStub;

    public PGetUserTrackingActivitiesResponse getUserTrackingActivities(PGetUserTrackingActivitiesRequest request) {
        return trackingServiceBlockingStub.getUserTrackingActivities(request);
    }
}
