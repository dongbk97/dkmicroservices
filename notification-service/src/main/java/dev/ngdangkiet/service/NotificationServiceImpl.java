package dev.ngdangkiet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Int64Value;
import dev.ngdangkiet.client.EmployeeGrpcClient;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeeResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsRequest;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PGetNotificationsResponse;
import dev.ngdangkiet.dkmicroservices.notification.protobuf.PNotification;
import dev.ngdangkiet.domain.NotificationEntity;
import dev.ngdangkiet.domain.notification.alert.JsonMessage;
import dev.ngdangkiet.domain.notification.alert.PayrollJsonMessage;
import dev.ngdangkiet.enums.department.Department;
import dev.ngdangkiet.enums.notification.NotificationType;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.error.ErrorHelper;
import dev.ngdangkiet.mapper.NotificationMapper;
import dev.ngdangkiet.mapper.RabbitMQNotificationMapper;
import dev.ngdangkiet.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final EmployeeGrpcClient employeeGrpcClient;

    private final NotificationRepository notificationRepository;
    private final RabbitMQNotificationMapper rabbitMQNotificationMapper = RabbitMQNotificationMapper.INSTANCE;
    private final NotificationMapper notificationMapper = NotificationMapper.INSTANCE;

    // RabbitMQ Services
    @Override
    public void receiveNewUserNotification(JsonMessage message) {
        NotificationEntity notification = rabbitMQNotificationMapper.toDomain(message);
        notificationRepository.save(notification);
    }

    @Override
    public void receiveNewApplicantNotification(JsonMessage message) {
        List<PEmployee> employeesInHRDepartment = employeeGrpcClient.getEmployees(PGetEmployeesRequest.newBuilder()
                .setDepartmentId(Department.HIRING_DEPARTMENT.getId())
                .setPositionId(-1L)
                .build()).getDataList();

        var notifications = employeesInHRDepartment.stream()
                .map(e -> NotificationEntity.builder()
                        .receiverId(e.getId())
                        .notificationType(NotificationType.ALERT.name())
                        .message(message.getMessage())
                        .build())
                .toList();

        notificationRepository.saveAll(notifications);
    }

    @Override
    public void receiveNewLeaveRequestNotification(JsonMessage message) {
        PEmployeeResponse response = employeeGrpcClient.getEmployeeById(message.getReceiverId());
        if (ErrorHelper.isFailed(response.getCode())) {
            throw new RuntimeException(String.format("Employee ID [{%d}] not found!", message.getReceiverId()));
        }
        notificationRepository.save(NotificationEntity.builder()
                .receiverId(message.getReceiverId())
                .notificationType(NotificationType.ALERT.name())
                .message(message.getMessage())
                .build());
    }

    @Override
    public void receiveNewUpdateLeaveRequestNotification(JsonMessage message) {
        notificationRepository.save(NotificationEntity.builder()
                .receiverId(message.getReceiverId())
                .notificationType(NotificationType.ALERT.name())
                .message(message.getMessage())
                .build());
    }

    // Notification Services
    @Override
    public PGetNotificationsResponse getNotifications(PGetNotificationsRequest request) {
        PGetNotificationsResponse.Builder builder = PGetNotificationsResponse.newBuilder()
                .setCode(ErrorCode.SUCCESS);

        try {
            List<NotificationEntity> notifications = notificationRepository.getNotifications(
                    request.getReceiverId(),
                    request.getAll(),
                    request.getSeen()
            );
            List<PNotification> pNotifications = notificationMapper.toProtobufs(notifications);
            builder.addAllNotifications(pNotifications);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public EmptyResponse seenOrUnseenNotification(Int64Value request) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            NotificationEntity notification = notificationRepository.findById(request.getValue()).orElse(null);
            if (Objects.isNull(notification)) {
                log.error("Notification [{}] not found!", request.getValue());
                builder.setCode(ErrorCode.INVALID_DATA);
            } else {
                notification.setSeen(!notification.isSeen());
                notificationRepository.save(notification);
                builder.setCode(ErrorCode.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    @Override
    public void receivePayrollNotification(PayrollJsonMessage payrollJsonMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        var notifications = payrollJsonMessage.getPayrollOfEachEmployees().stream()
                .map(payrollOfEachEmployee -> {
                    try {
                        return NotificationEntity.builder()
                                .notificationType(payrollJsonMessage.getNotificationType())
                                .receiverId(payrollOfEachEmployee.getEmployeeId())
                                .message(objectMapper.writeValueAsString(payrollOfEachEmployee))
                                .build();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        notificationRepository.saveAll(notifications);
    }
}
