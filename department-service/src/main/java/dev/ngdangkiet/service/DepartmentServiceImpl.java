package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartment;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentResponse;
import dev.ngdangkiet.dkmicroservices.department.protobuf.PDepartmentsResponse;
import dev.ngdangkiet.domain.DepartmentEntity;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.DepartmentMapper;
import dev.ngdangkiet.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper = DepartmentMapper.INSTANCE;

    @Override
    public Int64Value createOrUpdateDepartment(PDepartment pDepartment) {
        long response = ErrorCode.FAILED;
        try {
            if (pDepartment.getId() > 0 && departmentRepository.findById(pDepartment.getId()).isEmpty()) {
                response = ErrorCode.INVALID_DATA;
            } else {
                DepartmentEntity entity = departmentMapper.toDomain(pDepartment);
                response = departmentRepository.save(entity).getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int64Value.of(response);
    }

    @Override
    public PDepartmentResponse getDepartmentById(Int64Value departmentId) {
        PDepartmentResponse.Builder builder = PDepartmentResponse.newBuilder()
                .setCode(ErrorCode.FAILED);
        try {
            Optional<DepartmentEntity> entity = departmentRepository.findById(departmentId.getValue());
            if (entity.isPresent()) {
                builder.setCode(ErrorCode.SUCCESS)
                        .setData(departmentMapper.toProtobuf(entity.get()));
            } else {
                builder.setCode(ErrorCode.INVALID_DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public PDepartmentsResponse getDepartments() {
        PDepartmentsResponse.Builder builder = PDepartmentsResponse.newBuilder();
        try {
            List<DepartmentEntity> entities = departmentRepository.findAll();
            if (!CollectionUtils.isEmpty(entities)) {
                builder.addAllData(departmentMapper.toProtobufs(entities));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse deleteDepartmentById(Int64Value departmentId) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder()
                .setCode(ErrorCode.FAILED);
        try {
            Optional<DepartmentEntity> entity = departmentRepository.findById(departmentId.getValue());
            if (entity.isPresent()) {
                departmentRepository.deleteById(departmentId.getValue());
                builder.setCode(ErrorCode.SUCCESS);
            } else {
                builder.setCode(ErrorCode.INVALID_DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
