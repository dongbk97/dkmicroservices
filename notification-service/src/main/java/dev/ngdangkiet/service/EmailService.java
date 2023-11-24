package dev.ngdangkiet.service;

import dev.ngdangkiet.domain.JsonMessageEmail;
import dev.ngdangkiet.dto.EmailDTO;

import java.util.List;

public interface EmailService {

    void sendMail(EmailDTO email);

    void sendSampleEMail(String sendTo, String Subject, String body);

    void sendEmailWithTemplate(EmailDTO email);

    void sendEmailWithTemplate(List<EmailDTO> emails);


    void receiveEmailNotification(JsonMessageEmail message);

}
