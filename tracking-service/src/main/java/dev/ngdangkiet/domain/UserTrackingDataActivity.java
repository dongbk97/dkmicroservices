package dev.ngdangkiet.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
@Document(indexName = "user-tracking-data-activity")
public class UserTrackingDataActivity {

    @Id
    private String id = UUID.randomUUID().toString();

    @Field(type = FieldType.Text, name = "user_json")
    private String userJson;

    @Field(type = FieldType.Text, name = "ip_address")
    private String ipAddress;

    @Field(type = FieldType.Text, name = "request_url")
    private String requestUrl;

    @Field(type = FieldType.Keyword, name = "method")
    private String method;

    @Field(type = FieldType.Keyword, name = "action")
    private String action;

    @Field(type = FieldType.Text, name = "request_body_json")
    private String requestBodyJson;

    @Field(type = FieldType.Text, name = "response_body_json")
    private String responseBodyJson;

    @Field(type = FieldType.Long, name = "logged_time")
    private Long loggedTime;
}
