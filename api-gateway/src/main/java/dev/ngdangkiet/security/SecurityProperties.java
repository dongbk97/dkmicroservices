package dev.ngdangkiet.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkiet
 * @since 11/7/2023
 */

@Getter
@Setter
@Component
public class SecurityProperties {

    @Value("${security.auth.login-url}")
    private String loginUrl;
    @Value("${security.auth.header-name}")
    private String headerName;
    @Value("${security.auth.header-prefix}")
    private String headerPrefix;
    @Value("${security.auth.secret-key}")
    private String secretKey;
}
