package dev.ngdangkiet.service;

import dev.ngdangkiet.DTO.EmailDTO;
import dev.ngdangkiet.domain.JsonMessage;
import dev.ngdangkiet.domain.NotificationEntity;
import dev.ngdangkiet.mapper.EmailMapper;
import dev.ngdangkiet.mapper.NotificationMapper;
import dev.ngdangkiet.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final EmailMapper emailMapper = EmailMapper.INSTANCE;

    @Autowired
    EmailService emailService;


    @Override
    public void receiveWelcomeNotification(JsonMessage message) {
        NotificationEntity entity = notificationMapper.toDomain(message);
        notificationRepository.save(entity);
    }

    @Override
    public void receiveEmailActiveAccount(JsonMessage message) {
        EmailDTO email = emailMapper.toDomain(message);
        emailService.sendMail(email, false);
    }
}
