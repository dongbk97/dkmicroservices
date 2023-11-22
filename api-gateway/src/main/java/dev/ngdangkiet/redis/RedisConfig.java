package dev.ngdangkiet.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/21/2023
 */

@Configuration
@Slf4j
@EnableCaching
public class RedisConfig {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    public <V> void put(String key, V value, Duration duration) {
        if (Objects.isNull(value)) {
            log.warn("Cannot caching for key [{}] with null value", key);
            return;
        }

        try {
            redisTemplate().opsForValue().set(key, objectMapper.writeValueAsString(value), duration);
        } catch (JsonProcessingException e) {
            log.error("Failed to cache value for key [{}]. Error: [{}]", key, e.getMessage());
        }
    }

    public <V> void putAsList(String key, List<V> values, Duration duration) {
        if (CollectionUtils.isEmpty(values)) {
            log.warn("Cannot caching for key [{}] will null or empty values", key);
            return;
        }

        try {
            redisTemplate().opsForValue().set(key, objectMapper.writeValueAsString(values), duration);
        } catch (JsonProcessingException e) {
            log.error("Failed to cache values for key [{}]. Error: [{}]", key, e.getMessage());
        }
    }

    public <V> V get(String key, Class<V> clazz) {
        Object value = redisTemplate().opsForValue().get(key);

        if (Objects.isNull(value)) {
            log.debug("Cache miss for key [{}]", key);
            return null;
        }

        return objectMapper.convertValue(value, clazz);
    }

    public <V> V getByReadValueAsString(String key, Class<V> clazz) throws IOException {
        Object value = redisTemplate().opsForValue().get(key);

        if (Objects.isNull(value)) {
            log.debug("Cache miss for key [{}]", key);
            return null;
        }

        return objectMapper.readValue((String) value, clazz);
    }

    public <V> List<V> getAsList(String key, Class<V> clazz) {
        Object values = redisTemplate().opsForValue().get(key);
        if (Objects.isNull(values)) {
            log.debug("Cache miss for key {}", key);
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(values.toString(), new TypeReference<List<V>>() {
            }).stream().map(i -> objectMapper.convertValue(i, clazz)).toList();
        } catch (JsonProcessingException e) {
            log.error("Failed to get values for key [{}]. Error: [{}]", key, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
