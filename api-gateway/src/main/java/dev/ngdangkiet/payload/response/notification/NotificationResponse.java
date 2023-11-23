package dev.ngdangkiet.payload.response.notification;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 11/17/2023
 */

@Getter
@Setter
public class NotificationResponse {

    private Long id;
    private Long senderId;
    private String senderName;
    private String message;
    private String notificationType;
    private Boolean seen;
}
