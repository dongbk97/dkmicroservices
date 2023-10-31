package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.domain.PositionEntity;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.EmployeeMapper;
import dev.ngdangkiet.repository.EmployeeRepository;
import dev.ngdangkiet.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Override
    public Int64Value createOrUpdateEmployee(PEmployee pEmployee) {
        try {
            EmployeeEntity entity = employeeMapper.toDomain(pEmployee);
            Optional<PositionEntity> position = positionRepository.findById(pEmployee.getPositionId());
            if (position.isPresent()) {
                return Int64Value.of(employeeRepository.save(entity).getId());
            } else {
                return Int64Value.of(ErrorCode.INVALID_DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Int64Value.of(ErrorCode.FAILED);
    }

    @Override
    public PEmployeeResponse getEmployeeById(Int64Value employeeId) {
        try {
            Optional<EmployeeEntity> entity = employeeRepository.findById(employeeId.getValue());
            if (entity.isPresent()) {
                return PEmployeeResponse.newBuilder()
                        .setCode(ErrorCode.SUCCESS)
                        .setData(employeeMapper.toProtobuf(entity.get()))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PEmployeeResponse.newBuilder()
                .setCode(ErrorCode.FAILED)
                .build();
    }
}
