package dev.ngdangkiet.client;

import com.google.protobuf.StringValue;
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
}
