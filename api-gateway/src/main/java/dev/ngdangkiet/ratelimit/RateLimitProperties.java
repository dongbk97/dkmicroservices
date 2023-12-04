package dev.ngdangkiet.ratelimit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 12/4/2023
 */

@Getter
@Setter
@Component
public class RateLimitProperties {

    @Value("${rate-limit.limit}")
    private Integer limit;

    @Value("${rate-limit.seconds}")
    private Long seconds;
}
