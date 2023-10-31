package dev.ngdangkiet.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class ApiMessage {

    private int code;
    private String message;
    private Object data;
}
