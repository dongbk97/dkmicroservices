package dev.ngdangkiet.redis.utils;

import dev.ngdangkiet.constant.RedisCacheKeyConstant;
import dev.ngdangkiet.redis.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author ngdangkiet
 * @since 12/20/2023
 */

@RequiredArgsConstructor
@Component
public class CacheOTPUtil {

    private final RedisConfig redisConfig;

    public void cacheUserOTP(Long employeeId, String otp) {
        redisConfig.put(String.format(RedisCacheKeyConstant.Auth.USER_OTP_KEY, employeeId), otp, Duration.ofMinutes(3));
    }
}
