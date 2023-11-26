package dev.ngdangkiet.controller;

import dev.ngdangkiet.client.EmployeeGrpcClient;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PPosition;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.response.PositionMapper;
import dev.ngdangkiet.payload.request.employee.UpsertPositionRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @since 11/26/2023
 */

@Tag(name = "Position", description = "Position APIs")
@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
@Slf4j
public class PositionController {

    private final EmployeeGrpcClient employeeGrpcClient;
    private final PositionMapper positionMapper = PositionMapper.INSTANCE;

    @PostMapping
    public ApiMessage createPosition(@RequestBody UpsertPositionRequest request) {
        try {
            var response = employeeGrpcClient.createOrUpdatePosition(PPosition.newBuilder()
                    .setName(request.getName())
                    .build());
            return ErrorHelper.isSuccess((int) response) ? ApiMessage.success(response) : ApiMessage.failed((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @PutMapping
    public ApiMessage updatePosition(@RequestBody UpsertPositionRequest request) {
        try {
            var response = employeeGrpcClient.createOrUpdatePosition(PPosition.newBuilder()
                    .setId(request.getId())
                    .setName(request.getName())
                    .build());
            return ErrorHelper.isSuccess((int) response) ? ApiMessage.success(response) : ApiMessage.failed((int) response);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping
    public ApiMessage getPositions() {
        try {
            var response = employeeGrpcClient.getPositions();
            return ApiMessage.success(positionMapper.toDomains(response.getDataList()));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @GetMapping("/{id}")
    public ApiMessage getPositionById(@PathVariable(value = "id") Long positionId) {
        try {
            var response = employeeGrpcClient.getPositionById(positionId);
            return ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.success(positionMapper.toDomain(response.getData())) : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }

    @DeleteMapping("/{id}")
    public ApiMessage deletePositionById(@PathVariable(value = "id") Long positionId) {
        try {
            var response = employeeGrpcClient.deletePositionById(positionId);
            return ErrorHelper.isSuccess(response.getCode()) ? ApiMessage.SUCCESS : ApiMessage.failed(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiMessage.UNKNOWN_EXCEPTION;
        }
    }
}
