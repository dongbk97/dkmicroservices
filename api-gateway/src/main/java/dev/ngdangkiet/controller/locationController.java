package dev.ngdangkiet.controller;


import dev.ngdangkiet.client.LocationGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.location.protobuf.PLocationsResponse;
import dev.ngdangkiet.mapper.response.EmployeeResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class locationController {
    private final LocationGrpcClient locationGrpcClient;
//    private final EmployeeRequestMapper employeeRequestMapper = EmployeeRequestMapper.INSTANCE;
//    private final EmployeeResponseMapper employeeResponseMapper = EmployeeResponseMapper.INSTANCE;

    @GetMapping
    public ApiMessage getLocationsByName(@RequestParam String search) {
        PLocationsResponse response = locationGrpcClient.getLocationsByName(search);
        return ApiMessage.success(response.getAddressesList());
    }
}

