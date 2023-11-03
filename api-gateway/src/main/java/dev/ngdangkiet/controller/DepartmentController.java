package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.DepartmentGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.request.DepartmentRequestMapper;
import dev.ngdangkiet.mapper.response.DepartmentResponseMapper;
import dev.ngdangkiet.payload.request.DepartmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

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
            var data = departmentGrpcClient.createOrUpdateDepartment(departmentRequestMapper.toProtobuf(request));
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
    public ApiMessage updateDepartment(@RequestBody DepartmentRequest request) {
        try {
            var data = departmentGrpcClient.createOrUpdateDepartment(departmentRequestMapper.toProtobuf(request));
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
    public ApiMessage getDepartmentById(@PathVariable(value = "id") Long departmentId) {
        try {
            var grpcResponse = departmentGrpcClient.getDepartmentById(departmentId);

            if (Objects.isNull(grpcResponse)) {
                return ApiMessage.INVALID_DATA;
            }

            var data = departmentResponseMapper.toDomain(grpcResponse);
            return ApiMessage.success(data);
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
