package dev.ngdangkiet.service.impl;

import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.common.protobuf.EmptyResponse;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.redis.utils.CacheOTPUtil;
import dev.ngdangkiet.repository.EmployeeRepository;
import dev.ngdangkiet.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 12/20/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String mailFrom;

    private final CacheOTPUtil cacheOTPUtil;
    private final JavaMailSender javaMailSender;
    private final EmployeeRepository employeeRepository;

    @Override
    public EmptyResponse sendMailWithOtp(StringValue mailTo) {
        EmptyResponse.Builder builder = EmptyResponse.newBuilder().setCode(ErrorCode.FAILED);
        try {
            EmployeeEntity employee = employeeRepository.findEmployeeByEmail(mailTo.getValue()).orElse(null);
            if (Objects.isNull(employee)) {
                log.error("Email [{}] not found!", mailTo);
                return builder.setCode(ErrorCode.NOT_FOUND).build();
            }

            String text = String.format("<p>Hi <i>%s<i>. Your OTP for login: <strong>%s</strong>.</p>" +
                    "<p>The OTP will expire after <strong>3 minutes</strong>.</p>", employee.getFullName(), generateOtp(employee.getId()));

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setTo(mailTo.getValue());
            mimeMessageHelper.setSubject("Confirm your email");
            mimeMessageHelper.setFrom(mailFrom);
            mimeMessageHelper.setSentDate(new Date());
            javaMailSender.send(mimeMessage);
            log.info("Sending mail with OTP for employee email [{}]...", mailTo.getValue());
            builder.setCode(ErrorCode.SUCCESS);
        } catch (MessagingException | MailException e) {
            log.error("Failed to send email. Error [{}]", e.getMessage());
        }
        return builder.build();
    }

    private String generateOtp(Long employeeId) {
        String otp = RandomStringUtils.random(6, true, true);
        cacheOTPUtil.cacheUserOTP(employeeId, otp);
        return otp;
    }
}
