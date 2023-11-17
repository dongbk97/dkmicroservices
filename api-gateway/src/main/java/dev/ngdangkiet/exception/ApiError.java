package dev.ngdangkiet.exception;

import dev.ngdangkiet.util.DateTimeUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author ngdangkiet
 * @since 11/11/2023
 */

@Getter
@Setter
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class ApiError {

    private String path;
    private int statusCode;
    private String message;
    @Builder.Default
    private String dateTime = DateTimeUtil.formatLocalDateTimeNow();
    private Map<String, Object> errors;
}
