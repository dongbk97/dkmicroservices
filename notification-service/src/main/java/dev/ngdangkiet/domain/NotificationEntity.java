package dev.ngdangkiet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_notification")
public class NotificationEntity extends BaseEntity {

    private Long senderId;

    private String senderName;

    private Long receiverId;

    @Column(columnDefinition = "text")
    private String message;

    private String notificationType;

    private boolean seen;
}
