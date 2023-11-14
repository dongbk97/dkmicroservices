package dev.ngdangkiet.service;

import dev.ngdangkiet.domain.JsonMessage;
import dev.ngdangkiet.domain.NotificationEntity;
import dev.ngdangkiet.mapper.NotificationMapper;
import dev.ngdangkiet.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper = NotificationMapper.INSTANCE;

    @Override
    public void receiveWelcomeNotification(JsonMessage message) {
        NotificationEntity entity = notificationMapper.toDomain(message);
        notificationRepository.save(entity);
    }
}
