package dev.ngdangkiet.ratelimit;

import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Component
public class RefreshTokenCounter {

    private int counter;

    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }

    public synchronized void resetCounter() {
        counter = 0;
    }
}
