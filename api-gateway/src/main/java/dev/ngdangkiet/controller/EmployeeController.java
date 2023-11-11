package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.EmployeeGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.enums.Position;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.EmployeeRequestMapper;
import dev.ngdangkiet.mapper.response.EmployeeResponseMapper;
import dev.ngdangkiet.payload.request.EmployeeRequest;
import dev.ngdangkiet.payload.response.EmployeeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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
    public ApiMessage createEmployee(@Valid @RequestBody EmployeeRequest request) {
        try {
            var data = employeeGrpcClient.createOrUpdateEmployee(employeeRequestMapper.toProtobuf(request));
            if (ErrorHelper.isFailed((int) data)) {
                return ApiMessage.CREATE_FAILED;
            }
            return ApiMessage.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateEmployee(@Valid @RequestBody EmployeeRequest request) {
        try {
            var data = employeeGrpcClient.createOrUpdateEmployee(employeeRequestMapper.toProtobuf(request));
            if (ErrorHelper.isFailed((int) data)) {
                return ApiMessage.UPDATE_FAILED;
            }
            return ApiMessage.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/{id}")
    public ApiMessage getEmployeeById(@PathVariable(value = "id") Long id) {
        try {
            var grpcResponse = employeeGrpcClient.getEmployeeById(id);

            if (Objects.isNull(grpcResponse)) {
                return ApiMessage.INVALID_DATA;
            }

            var position = EmployeeResponse.Position.builder()
                    .id(grpcResponse.getPositionId())
                    .name(Position.of(grpcResponse.getPositionId()).getName())
                    .build();
            var data = employeeResponseMapper.toDomain(grpcResponse);
            data.setPosition(position);

            return ApiMessage.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping
    public ApiMessage getEmployees(@RequestParam(name = "departmentId", required = false) Long departmentId,
                                   @RequestParam(name = "positionId", required = false) Long positionId) {
        try {
            var grpcResponse = employeeGrpcClient.getEmployees(
                    PGetEmployeesRequest.newBuilder()
                            .setDepartmentId(ObjectUtils.defaultIfNull(departmentId, -1L))
                            .setPositionId(ObjectUtils.defaultIfNull(positionId, -1L))
                            .build()
            );
            return ApiMessage.success(employeeResponseMapper.toDomains(grpcResponse.getDataList()));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @DeleteMapping("/{id}")
    public ApiMessage deleteEmployee(@PathVariable(value = "id") Long id) {
        try {
            var grpcResponse = employeeGrpcClient.deleteEmployeeById(id);
            if (ErrorHelper.isSuccess(grpcResponse.getCode())) {
                return ApiMessage.SUCCESS;
            }
            return ApiMessage.DELETE_FAILED;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
