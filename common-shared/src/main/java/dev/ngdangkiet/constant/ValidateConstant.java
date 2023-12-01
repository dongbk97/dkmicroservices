package dev.ngdangkiet.constant;

/**
 * @author ngdangkiet
 * @since 11/10/2023
 */

public class ValidateConstant {

    public static class Password {

        public static final int MIN_SIZE = 8;
        public static final int MAX_SIZE = 16;
        public static final String PATTERN_PASSWORD = "^(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-])(?=.*[A-Za-z])(?=.*\\d).+$";
    }

    public static class PhoneNumber {

        public static final String PHONE_NUMBER_PATTERN = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
    }
}
