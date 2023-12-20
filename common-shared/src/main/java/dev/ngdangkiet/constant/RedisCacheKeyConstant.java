package dev.ngdangkiet.constant;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

public class RedisCacheKeyConstant {

    public static class Tracking {

        public static final String USER_TRACKING_KEY = "user_tracking_key_%s_%s";
    }

    public static class Auth {

        public static final String USER_OTP_KEY = "user_%s_otp";
    }
}
