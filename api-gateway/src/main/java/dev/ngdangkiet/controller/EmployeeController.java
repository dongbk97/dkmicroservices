package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.EmployeeGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.constant.PositionConstant;
import dev.ngdangkiet.mapper.request.EmployeeRequestMapper;
import dev.ngdangkiet.mapper.response.EmployeeResponseMapper;
import dev.ngdangkiet.payload.request.EmployeeRequest;
import dev.ngdangkiet.payload.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeGrpcClient employeeGrpcClient;
    private final EmployeeRequestMapper employeeRequestMapper = EmployeeRequestMapper.INSTANCE;
    private final EmployeeResponseMapper employeeResponseMapper = EmployeeResponseMapper.INSTANCE;

    @PostMapping
    public ApiMessage createEmployee(@RequestBody EmployeeRequest request) {
        try {
            var data = employeeGrpcClient.createOrUpdateEmployee(employeeRequestMapper.toProtobuf(request));
            return ApiMessage.success(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiMessage.CREATE_FAILED;
    }

    @GetMapping("/{id}")
    public ApiMessage getEmployeeById(@PathVariable(value = "id") Long id) {
        try {
            var grpcResponse = employeeGrpcClient.getEmployeeById(id);

            var position = EmployeeResponse.Position.builder()
                    .id(grpcResponse.getId())
                    .name(PositionConstant.of(grpcResponse.getId()).getName())
                    .build();
            var data = employeeResponseMapper.toDomain(grpcResponse);
            data.setPosition(position);

            return ApiMessage.success(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiMessage.CREATE_FAILED;
    }
}
