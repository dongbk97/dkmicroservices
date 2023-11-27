package dev.ngdangkiet.client;

import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PGetLocationRequest;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationResponse;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationsResponse;
import dev.ngdangkiet.dkmicroservices.location.service.LocationServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Component
@RequiredArgsConstructor
public class LocationGrpcClient {

    @GrpcClient("grpc-location-service")
    private final LocationServiceGrpc.LocationServiceBlockingStub locationServiceBlockingStub;

    public PLocationsResponse getLocationsByName(String search) {
        return locationServiceBlockingStub.getLocationByName(StringValue.of(search));
    }

    public PLocationResponse calculateDistanceFrom2Point(String from, String to) {
        return locationServiceBlockingStub.calculateDistanceFrom2Point(PGetLocationRequest.newBuilder()
                .setFromLocation(from)
                .setToLocation(to)
                .build());
    }
}
