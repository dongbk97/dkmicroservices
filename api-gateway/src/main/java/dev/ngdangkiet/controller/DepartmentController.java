package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.DepartmentGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.DepartmentRequestMapper;
import dev.ngdangkiet.mapper.response.DepartmentResponseMapper;
import dev.ngdangkiet.payload.request.department.DepartmentRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Tag(name = "Department", description = "Department APIs")
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentGrpcClient departmentGrpcClient;
    private final DepartmentRequestMapper departmentRequestMapper = DepartmentRequestMapper.INSTANCE;
    private final DepartmentResponseMapper departmentResponseMapper = DepartmentResponseMapper.INSTANCE;

    @PostMapping
    public ApiMessage createDepartment(@RequestBody DepartmentRequest request) {
        try {
            return getApiMessageForUpsertDepartment(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updateDepartment(@RequestBody DepartmentRequest request) {
        try {
            return getApiMessageForUpsertDepartment(request);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    private ApiMessage getApiMessageForUpsertDepartment(DepartmentRequest request) {
        var data = departmentGrpcClient.createOrUpdateDepartment(departmentRequestMapper.toProtobuf(request));
        return ErrorHelper.isSuccess((int) data) ? ApiMessage.success(data) : ApiMessage.failed((int) data);
    }

    @GetMapping("/{id}")
    public ApiMessage getDepartmentById(@PathVariable(value = "id") Long departmentId) {
        try {
            var grpcResponse = departmentGrpcClient.getDepartmentById(departmentId);

            return ErrorHelper.isSuccess(grpcResponse.getCode())
                    ? ApiMessage.success(departmentResponseMapper.toDomain(grpcResponse.getData()))
                    : ApiMessage.failed(grpcResponse.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping
    public ApiMessage getDepartments() {
        try {
            var grpcResponse = departmentGrpcClient.getDepartments();
            return ApiMessage.success(departmentResponseMapper.toDomains(grpcResponse.getDataList()));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @DeleteMapping("/{id}")
    public ApiMessage deleteDepartmentById(@PathVariable(value = "id") Long departmentId) {
        try {
            var grpcResponse = departmentGrpcClient.deleteDepartmentById(departmentId);
            return ErrorHelper.isSuccess(grpcResponse.getCode()) ? ApiMessage.SUCCESS : ApiMessage.failed(grpcResponse.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
