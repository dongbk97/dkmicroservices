package dev.ngdangkiet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class EmailDTO {

    private Long receiverId;
    private String receiverEmail;
    private String message;
    private String subject;
    private String attachment;
    private Map<String, Object> properties;
    private String emailTemplate;
    private Boolean isHTML = Boolean.FALSE;
}
