package dev.ngdangkiet.error;

import com.google.protobuf.Int32Value;

/**
 * @author ngdangkiet
 * @since 10/31/2023
 */

public class ErrorHelper {

    public static boolean isSuccess(int code) {
        return code >= 0;
    }

    public static boolean isSuccess(Int32Value code) {
        return isSuccess(code.getValue());
    }

    public static boolean isFailed(int code) {
        return code < 0;
    }

    public static boolean isFailed(Int32Value code) {
        return isFailed(code.getValue());
    }
}
