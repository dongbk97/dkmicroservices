package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeesResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.domain.PositionEntity;
import dev.ngdangkiet.encoder.PBKDF2Encoder;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.mapper.EmployeeMapper;
import dev.ngdangkiet.rabbitmq.RabbitMQProducer;
import dev.ngdangkiet.repository.EmployeeRepository;
import dev.ngdangkiet.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
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
    private final PBKDF2Encoder pbkdf2Encoder;
    private final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
    private final RabbitMQProducer rabbitMQProducer;

    @Override
    public Int64Value createOrUpdateEmployee(PEmployee pEmployee) {
        long response = ErrorCode.FAILED;
        try {
            boolean isNewEmployee = pEmployee.getId() <= 0;
            EmployeeEntity employee = null;

            if (!isNewEmployee) {
                employee = employeeRepository.findById(pEmployee.getId()).orElse(null);
                if (Objects.isNull(employee)) {
                    log.error("User [{}] not found!", pEmployee.getId());
                    return Int64Value.of(ErrorCode.INVALID_DATA);
                }
            }

            if (existsEmail(pEmployee.getId(), pEmployee.getEmail())) {
                log.error("Email [{}] already exists!", pEmployee.getEmail());
                return Int64Value.of(ErrorCode.INVALID_DATA);
            }

            Optional<PositionEntity> position = positionRepository.findById(pEmployee.getPositionId());
            if (position.isEmpty()) {
                log.error("Position [{}] not found!", pEmployee.getPositionId());
                return Int64Value.of(ErrorCode.INVALID_DATA);
            }

            EmployeeEntity entity = employeeMapper.toDomain(pEmployee);
            entity.setPosition(position.get());
            entity.setPassword(isNewEmployee ? pbkdf2Encoder.encode(entity.getPassword()) : employee.getPassword());
            response = employeeRepository.save(entity).getId();

            // send notification to new user
            if (isNewEmployee) {
                rabbitMQProducer.sendWelcomeNotification(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Int64Value.of(response);
    }

    private boolean existsEmail(Long employeeId, String email) {
        Optional<EmployeeEntity> employee = employeeRepository.findByEmail(email);
        return employee.isPresent() && (employeeId <= 0 || !employeeId.equals(employee.get().getId()));
    }

    @Override
    public PEmployeeResponse getEmployeeById(Int64Value employeeId) {
        PEmployeeResponse.Builder builder = PEmployeeResponse.newBuilder()
                .setCode(ErrorCode.FAILED);
        try {
            Optional<EmployeeEntity> entity = employeeRepository.findById(employeeId.getValue());
            if (entity.isPresent()) {
                builder.setCode(ErrorCode.SUCCESS)
                        .setData(employeeMapper.toProtobuf(entity.get()));
            } else {
                builder.setCode(ErrorCode.INVALID_DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public PEmployeeResponse getEmployeeByEmail(StringValue request) {
        PEmployeeResponse.Builder builder = PEmployeeResponse.newBuilder()
                .setCode(ErrorCode.FAILED);
        try {
            Optional<EmployeeEntity> entity = employeeRepository.findByEmail(request.getValue());
            if (entity.isPresent()) {
                builder.setCode(ErrorCode.SUCCESS)
                        .setData(employeeMapper.toProtobuf(entity.get()));
            } else {
                builder.setCode(ErrorCode.INVALID_DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    @Override
    public PEmployeesResponse getEmployees(PGetEmployeesRequest request) {
        PEmployeesResponse.Builder builder = PEmployeesResponse.newBuilder().setCode(ErrorCode.SUCCESS);
        try {
            List<EmployeeEntity> entities = employeeRepository.findByDepartmentAndPosition(request.getDepartmentId(), request.getPositionId());
            if (!CollectionUtils.isEmpty(entities)) {
                builder.addAllData(employeeMapper.toProtobufs(entities));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse deleteEmployeeById(Int64Value request) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder()
                .setCode(ErrorCode.FAILED);
        try {
            Optional<EmployeeEntity> entity = employeeRepository.findById(request.getValue());
            if (entity.isPresent()) {
                employeeRepository.deleteById(request.getValue());
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
