package dev.ngdangkiet.payload.response.attendance;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkiet
 * @since 12/5/2023
 */

@Getter
@Setter
@Builder(setterPrefix = "set")
public class TotalWorkingDayOfUserResponse {

    private int totalDayOfMonth;
    private double currentSystemTotalDayWorking;
    private double currentUserTotalDayWorking;
}