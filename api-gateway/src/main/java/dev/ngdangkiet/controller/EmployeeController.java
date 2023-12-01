package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.EmployeeGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.employee.CreateEmployeeRequestMapper;
import dev.ngdangkiet.mapper.request.employee.UpdateEmployeeRequestMapper;
import dev.ngdangkiet.mapper.response.employee.EmployeeDetailResponseMapper;
import dev.ngdangkiet.mapper.response.employee.EmployeeListResponseMapper;
import dev.ngdangkiet.payload.request.employee.CreateEmployeeRequest;
import dev.ngdangkiet.payload.request.employee.UpdateEmployeeRequest;
import dev.ngdangkiet.payload.response.auth.LoginResponse;
import dev.ngdangkiet.security.SecurityHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Tag(name = "Employee", description = "Employee APIs")
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeGrpcClient employeeGrpcClient;
    private final CreateEmployeeRequestMapper createEmployeeRequestMapper = CreateEmployeeRequestMapper.INSTANCE;
    private final UpdateEmployeeRequestMapper updateEmployeeRequestMapper = UpdateEmployeeRequestMapper.INSTANCE;
    private final EmployeeListResponseMapper employeeListResponseMapper = EmployeeListResponseMapper.INSTANCE;
    private final EmployeeDetailResponseMapper employeeDetailResponseMapper = EmployeeDetailResponseMapper.INSTANCE;

    @PostMapping
    public ApiMessage createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        try {
            var data = employeeGrpcClient.createOrUpdateEmployee(createEmployeeRequestMapper.toProtobuf(request));
            return getApiMessageForUpsertEmployee((int) data);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateEmployee(@Valid @RequestBody UpdateEmployeeRequest request) {
        try {
            var data = employeeGrpcClient.createOrUpdateEmployee(updateEmployeeRequestMapper.toProtobuf(request));
            return getApiMessageForUpsertEmployee((int) data);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    private ApiMessage getApiMessageForUpsertEmployee(int code) {
        return ErrorHelper.isSuccess(code) ? ApiMessage.success(code) : ApiMessage.failed(code);
    }

    @GetMapping("/{id}")
    public ApiMessage getEmployeeById(@PathVariable(value = "id") Long id) {
        try {
            var grpcResponse = employeeGrpcClient.getEmployeeById(id);

            if (ErrorHelper.isFailed(grpcResponse.getCode())) {
                return ApiMessage.failed(grpcResponse.getCode());
            }

            return ApiMessage.success(employeeDetailResponseMapper.toDomain(grpcResponse.getData()));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping
    public ApiMessage getEmployees(@RequestParam(name = "departmentId", required = false) Long departmentId,
                                   @RequestParam(name = "positionId", required = false) Long positionId) {
        try {
            // Test get from Security Context Holder
            LoginResponse userLogged = SecurityHelper.getUserLogin();
            assert userLogged != null;
            log.info(userLogged.toString());
            // -------------------------------------
            var grpcResponse = employeeGrpcClient.getEmployees(
                    PGetEmployeesRequest.newBuilder()
                            .setDepartmentId(ObjectUtils.defaultIfNull(departmentId, -1L))
                            .setPositionId(ObjectUtils.defaultIfNull(positionId, -1L))
                            .build()
            );
            return ApiMessage.success(employeeListResponseMapper.toDomains(grpcResponse.getDataList()));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @DeleteMapping("/{id}")
    public ApiMessage deleteEmployee(@PathVariable(value = "id") Long id) {
        try {
            var grpcResponse = employeeGrpcClient.deleteEmployeeById(id);
            return ErrorHelper.isSuccess(grpcResponse.getCode()) ? ApiMessage.SUCCESS : ApiMessage.failed(grpcResponse.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
