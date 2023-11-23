package dev.ngdangkiet.domain.tracking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ngdangkiet
 * @since 11/20/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class UserActivityData implements Serializable {

    private Long userId;
    private String ipAddress;
    private String requestUrl;
    private String method;
    private String action;
    private String requestBodyJson;
    private String responseBodyJson;
    private Long loggedTime;
}
