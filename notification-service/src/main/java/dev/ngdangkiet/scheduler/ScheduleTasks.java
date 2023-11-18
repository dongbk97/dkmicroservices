package dev.ngdangkiet.scheduler;

import dev.ngdangkiet.client.EmployeeGrpcClient;
import dev.ngdangkiet.constant.MessageConstant;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployeesResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PGetEmployeesRequest;
import dev.ngdangkiet.domain.NotificationEntity;
import dev.ngdangkiet.enums.Holiday;
import dev.ngdangkiet.enums.NotificationType;
import dev.ngdangkiet.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 11/18/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleTasks {

    private final EmployeeGrpcClient employeeGrpcClient;
    private final NotificationRepository notificationRepository;

    // Every day at 7:00 AM
    @Scheduled(cron = "0 0 7 * * ?")
    public void executeHolidayTask() {
        List<NotificationEntity> notifications = new ArrayList<>();
        for (Holiday holiday : Holiday.values()) {
            if (isDateNow(holiday.getDate())) {
                log.info("Holiday [{}]", holiday.name());
                PEmployeesResponse employees = employeeGrpcClient.getEmployees(PGetEmployeesRequest.newBuilder()
                        .setDepartmentId(-1L)
                        .setPositionId(-1L)
                        .build());
                if (!employees.getDataList().isEmpty()) {
                    employees.getDataList().forEach(e -> notifications.add(NotificationEntity.builder()
                            .setReceiverId(e.getId())
                            .setNotificationType(NotificationType.ALERT.name())
                            .setMessage(holiday.getMessage())
                            .build()));
                }
                break;
            }
        }

        notificationRepository.saveAll(notifications);
    }

    // Every day at 8:00 AM
    @Scheduled(cron = "0 0 8 * * ?")
    public void executeBirthdayTask() {
        List<NotificationEntity> notifications = new ArrayList<>();
        PEmployeesResponse employees = employeeGrpcClient.getEmployees(PGetEmployeesRequest.newBuilder()
                .setDepartmentId(-1L)
                .setPositionId(-1L)
                .build());
        if (!employees.getDataList().isEmpty()) {
            employees.getDataList().forEach(e -> {
                if (!e.getBirthDay().equals(EMPTY) && isDateNow(LocalDate.parse(e.getBirthDay(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                    log.info("Birthday [{}]", e.getFullName());
                    notifications.add(NotificationEntity.builder()
                            .setReceiverId(e.getId())
                            .setMessage(MessageConstant.Notification.BirthDay.HAPPY_BIRTHDAY)
                            .setNotificationType(NotificationType.ALERT.name())
                            .build());
                }
            });
        }

        notificationRepository.saveAll(notifications);
    }

    private boolean isDateNow(LocalDate date) {
        LocalDate now = LocalDate.now();
        return date.getDayOfMonth() == now.getDayOfMonth() && date.getMonthValue() == now.getMonthValue();
    }
}
