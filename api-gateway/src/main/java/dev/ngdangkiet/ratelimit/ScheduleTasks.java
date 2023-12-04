package dev.ngdangkiet.ratelimit;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Component
@RequiredArgsConstructor
public class ScheduleTasks {

    private final RefreshTokenCounter refreshTokenCounter;

    @Scheduled(fixedRate = 60000)
    public void executeResetCounter() {
        refreshTokenCounter.resetCounter();
    }
}
