package dev.ngdangkiet.server;

import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PGetLocationRequest;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationResponse;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationsResponse;
import dev.ngdangkiet.dkmicroservices.location.service.LocationServiceGrpc;
import dev.ngdangkiet.service.LocationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

/**
 * @author trongld
 * @since 11/09/2023
 */

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class LocationServiceGrpcServer extends LocationServiceGrpc.LocationServiceImplBase {

    private final LocationService locationService;
    @Override
    public void getLocationByName(StringValue request, StreamObserver<PLocationsResponse> responseObserver) {
        PLocationsResponse response = locationService.getSameNameLocation(request.getValue());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void calculateDistanceFrom2Point(PGetLocationRequest request, StreamObserver<PLocationResponse> responseObserver) {
        responseObserver.onNext(locationService.calculateDistanceFrom2Point(request));
        responseObserver.onCompleted();
    }
}
