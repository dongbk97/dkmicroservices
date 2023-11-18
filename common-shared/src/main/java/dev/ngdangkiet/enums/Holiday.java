package dev.ngdangkiet.enums;

import dev.ngdangkiet.constant.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * @author ngdangkiet
 * @since 11/18/2023
 */

@AllArgsConstructor
@Getter
public enum Holiday {

    LUNAR_YEAR(LocalDate.of(2000, 1, 1), MessageConstant.Notification.Holiday.LUNAR_YEAR),
    NATIONAL_DAY(LocalDate.of(2000, 9, 2), MessageConstant.Notification.Holiday.NATIONAL_DAY),
    INTERNATIONAL_WOMEN_DAY(LocalDate.of(2000, 3, 8), MessageConstant.Notification.Holiday.INTERNATIONAL_WOMEN_DAY),
    MID_AUTUMN_FESTIVAL(LocalDate.of(2000, 6, 1), MessageConstant.Notification.Holiday.MID_AUTUMN_FESTIVAL),
    TEACHER_DAY(LocalDate.of(2000, 11, 20), MessageConstant.Notification.Holiday.TEACHER_DAY);

    private final LocalDate date;
    private final String message;
}
