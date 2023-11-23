package dev.ngdangkiet.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ngdangkiet.constant.RedisCacheKeyConstant;
import dev.ngdangkiet.domain.tracking.TrackingJson;
import dev.ngdangkiet.domain.tracking.UserActivityData;
import dev.ngdangkiet.rabbitmq.RabbitMQProducer;
import dev.ngdangkiet.redis.RedisConfig;
import dev.ngdangkiet.security.SecurityHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 11/20/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiInterceptor implements WebFilter {

    private static final List<String> POSSIBLE_IP_HEADERS = List.of(
            "X-Forwarded-For",
            "HTTP_FORWARDED",
            "HTTP_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_CLIENT_IP",
            "HTTP_VIA",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "REMOTE_ADDR"
    );
    private final ObjectMapper objectMapper;
    private final RabbitMQProducer rabbitMQProducer;
    private final RedisConfig redisConfig;

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        var userLogged = SecurityHelper.getUserLogin();

        if (Objects.isNull(userLogged)) {
            return chain.filter(exchange);
        }

        return chain.filter(exchange).doFinally(signalType -> {
            log.info("postHandle ...");
            try {
                ServerHttpRequest request = exchange.getRequest();
                TrackingJson trackingJson = redisConfig.getByReadValueAsString(String.format(RedisCacheKeyConstant.Tracking.USER_TRACKING_KEY, userLogged.getToken(), userLogged.getUserInfo().getId()), TrackingJson.class);

                UserActivityData userActivityData = UserActivityData.builder()
                        .setUserId(userLogged.getUserInfo().getId())
                        .setAction(trackingJson.getAction())
                        .setIpAddress(ObjectUtils.defaultIfNull(getIpAddressFromHeader(request), EMPTY))
                        .setRequestUrl(request.getURI().toString())
                        .setMethod(request.getMethod().toString())
                        .setRequestBodyJson(trackingJson.getRequestBodyJson())
                        .setResponseBodyJson(trackingJson.getResponseBodyJson())
                        .setLoggedTime(System.currentTimeMillis())
                        .build();

                rabbitMQProducer.trackingUserActivity(userActivityData);
            } catch (JsonProcessingException e) {
                log.error("Failed when convert object to string: {}", e.getMessage());
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String getIpAddressFromHeader(ServerHttpRequest request) {
        for (String ipHeader : POSSIBLE_IP_HEADERS) {
            String headerValue = request.getHeaders()
                    .getValuesAsList(ipHeader)
                    .stream()
                    .filter(StringUtils::hasLength)
                    .findFirst()
                    .orElse(null);

            if (Objects.nonNull(headerValue)) {
                return headerValue;
            }
        }

        return null;
    }
}
