package dev.ngdangkiet.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/14/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class JsonMessage implements Serializable {

    private Long senderId;
    private Long receiverId;
    private String message;
    private String notificationType;
}
