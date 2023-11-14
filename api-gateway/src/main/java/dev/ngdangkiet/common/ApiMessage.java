package dev.ngdangkiet.common;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

@Getter
@Setter
public class ApiMessage {

    public static ApiMessage SUCCESS = new ApiMessage(0, "Success");
    public static ApiMessage FAILED = new ApiMessage(-1, "Failed");
    public static ApiMessage INVALID_DATA = new ApiMessage(-2, "Invalid data");
    public static ApiMessage CREATE_FAILED = new ApiMessage(-3, "Create failed");
    public static ApiMessage UPDATE_FAILED = new ApiMessage(-4, "Update failed");
    public static ApiMessage DELETE_FAILED = new ApiMessage(-5, "Delete failed");
    public static ApiMessage UNKNOWN_EXCEPTION = new ApiMessage(-6, "Unknown exception");

    public static ApiMessage UNAUTHORIZED = new ApiMessage(-10, "Unauthorized");
    public static ApiMessage LOGIN_FAILED = new ApiMessage(-11, "Login failed");

    private int code;
    private String message;
    private LocalDateTime dateTime;
    private Object data;

    public ApiMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public static ApiMessage success(Object data) {
        ApiMessage apiMessage = ApiMessage.SUCCESS;
        apiMessage.setData(data);
        return apiMessage;
    }
}
