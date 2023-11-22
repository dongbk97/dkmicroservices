package dev.ngdangkiet.service;

import dev.ngdangkiet.dto.EmailDTO;
import dev.ngdangkiet.domain.notification.JsonMessage;

public interface EmailService {

    void sendMail(EmailDTO email, boolean isHTML);

    void sendMail(String sendTo, String Subject, String body);

    void sendEmailWithTemplate(EmailDTO email, String templateName);

    void receiveEmailNotification(JsonMessage message);

}
