package dev.ngdangkiet.redis.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ngdangkiet.common.ApiMessage;
import dev.ngdangkiet.constant.RedisCacheKeyConstant;
import dev.ngdangkiet.domain.tracking.TrackingJson;
import dev.ngdangkiet.redis.RedisConfig;
import dev.ngdangkiet.security.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

@RequiredArgsConstructor
@Component
public class CacheTrackingUtil {

    private final RedisConfig redisConfig;
    private final ObjectMapper objectMapper;

    public void cacheTrackingJson(String action, Object request, ApiMessage response) throws JsonProcessingException {
        var userLogged = SecurityHelper.getUserLogin();
        assert userLogged != null;
        redisConfig.put(String.format(RedisCacheKeyConstant.Tracking.USER_TRACKING_KEY, userLogged.getToken(), userLogged.getUserInfo().getId()),
                TrackingJson.builder()
                        .setAction(action)
                        .setRequestBodyJson(objectMapper.writeValueAsString(request))
                        .setResponseBodyJson(objectMapper.writeValueAsString(response))
                        .build(),
                Duration.ofMinutes(2));
    }
}
