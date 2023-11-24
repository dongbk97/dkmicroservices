package dev.ngdangkiet.constant;

/**
 * @author ngdangkiet
 * @since 11/10/2023
 */

public class MessageConstant {

    public static class Employee {
        public static final String INVALID_GENDER = "employee.invalid.gender";
        public static final String INVALID_PASSWORD = "employee.invalid.password";
        public static final String WELCOME_NEW_USER = "Welcome To Join Our Team!";
    }

    public static class Notification {
        public static class BeginEndDayOfWeek {
            public static final String BEGIN_DAY_OF_WEEK = "Wishing everyone a happy and smooth start to the work week, everything goes smoothly and without any difficulties!";
            public static final String END_DAY_OF_WEEK = "Wishing you a healthy, fun and happy weekend!";
        }

        public static class Holiday {
            public static final String LUNAR_YEAR = "Happy Lunar year <3";
            public static final String NATIONAL_DAY = "Happy National Day <3";
            public static final String INTERNATIONAL_WOMEN_DAY = "Happy International Women's Day <3";
            public static final String MID_AUTUMN_FESTIVAL = "Happy Mid Autumn Festival <3";
            public static final String TEACHER_DAY = "Happy Teacher Day <3";
        }

        public static class BirthDay {
            public static final String HAPPY_BIRTHDAY = "Happy BirthDay to you <3";
        }
    }
}
