package dev.ngdangkiet.enums;

/**
 * @author TrongLD
 * @since 11/16/2023
 */

public enum EmailTemplate {
    INDEX("index.html"),
    HAPPY_BIRTHDAY("Happy_birthday.html"),
    WELCOME_NEW_USER("Welcome_New_User.html");

    private final String value;

    private EmailTemplate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
