package dev.ngdangkiet.DTO;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private Long receiverId;
    private String receiverEmail;
    private String message;
    private String subject;
    private String attachment;
    private Map<String, Object> properties;
    private String emailTemplate;
}
