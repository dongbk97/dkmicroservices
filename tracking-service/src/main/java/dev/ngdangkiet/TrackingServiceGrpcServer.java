package dev.ngdangkiet;

import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesRequest;
import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesResponse;
import dev.ngdangkiet.dkmicroservices.tracking.service.TrackingServiceGrpc;
import dev.ngdangkiet.service.UserTrackingDataActivityService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

@GrpcService
@RequiredArgsConstructor
public class TrackingServiceGrpcServer extends TrackingServiceGrpc.TrackingServiceImplBase {

    private final UserTrackingDataActivityService userTrackingDataActivityService;

    @Override
    public void getUserTrackingActivities(PGetUserTrackingActivitiesRequest request, StreamObserver<PGetUserTrackingActivitiesResponse> responseObserver) {
        PGetUserTrackingActivitiesResponse response = userTrackingDataActivityService.getUserTrackingActivities(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
