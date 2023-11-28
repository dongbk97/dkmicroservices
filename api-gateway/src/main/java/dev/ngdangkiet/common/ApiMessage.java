package dev.ngdangkiet.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.util.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiMessage {

    public static ApiMessage SUCCESS = new ApiMessage(0, "Success");
    public static ApiMessage FAILED = new ApiMessage(-1, "Failed");
    public static ApiMessage INVALID_DATA = new ApiMessage(-2, "Invalid data");
    public static ApiMessage NOT_FOUND = new ApiMessage(-3, "Not found");
    public static ApiMessage ALREADY_EXISTS = new ApiMessage(-4, "Already Exists");
    public static ApiMessage UNKNOWN_EXCEPTION = new ApiMessage(-5, "Unknown exception");

    public static ApiMessage UNAUTHORIZED = new ApiMessage(-10, "Unauthorized");
    public static ApiMessage LOGIN_FAILED = new ApiMessage(-11, "Login failed");

    private int code;
    private String message;
    private String localDateTime;
    private Object data;

    public ApiMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.localDateTime = DateTimeUtil.formatLocalDateTimeNow();
    }

    public static ApiMessage success(Object data) {
        ApiMessage apiMessage = ApiMessage.SUCCESS.clone();
        apiMessage.setData(data);
        return apiMessage;
    }

    public static ApiMessage failed(int code) {
        return switch (code) {
            case ErrorCode.INVALID_DATA -> ApiMessage.INVALID_DATA;
            case ErrorCode.NOT_FOUND -> ApiMessage.NOT_FOUND;
            case ErrorCode.ALREADY_EXISTS -> ApiMessage.ALREADY_EXISTS;
            default -> ApiMessage.FAILED;
        };
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ApiMessage clone() {
        ApiMessage instance = new ApiMessage(code, message);
        instance.setData(this.getData());
        return instance;
    }
}
