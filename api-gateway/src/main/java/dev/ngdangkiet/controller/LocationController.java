package dev.ngdangkiet.controller;


import dev.ngdangkiet.client.LocationGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Location", description = "Location APIs")
@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationGrpcClient locationGrpcClient;

    @GetMapping
    public ApiMessage getLocationsByName(@RequestParam String search) {
        PLocationsResponse response = locationGrpcClient.getLocationsByName(search);
        return ApiMessage.success(response.getAddressesList());
    }
}

