package dev.ngdangkiet.service.impl;

import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PChangePasswordRequest;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.encoder.PBKDF2Encoder;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.rabbitmq.RabbitMQProducer;
import dev.ngdangkiet.repository.EmployeeRepository;
import dev.ngdangkiet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 11/16/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final EmployeeRepository employeeRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final PBKDF2Encoder pbkdf2Encoder;

    @Override
    public EmptyResponse changePassword(PChangePasswordRequest request) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);

        if (List.of(request.getCurrentPassword(), request.getNewPassword()).contains(EMPTY)) {
            return builder.setCode(ErrorCode.INVALID_DATA).build();
        }

        try {
            Optional<EmployeeEntity> employee = employeeRepository.findById(request.getUserId());
            if (employee.isPresent() && employee.get().getPassword().equals(pbkdf2Encoder.encode(request.getCurrentPassword()))) {
                employee.get().setPassword(pbkdf2Encoder.encode(request.getNewPassword()));
                employeeRepository.save(employee.get());
                rabbitMQProducer.sendChangePasswordNotification(employee.get().getId());
                return builder.setCode(ErrorCode.SUCCESS).build();
            } else {
                return builder.setCode(ErrorCode.INVALID_DATA).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
