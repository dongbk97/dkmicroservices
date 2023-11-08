package dev.ngdangkiet.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author ngdangkiet
 * @since 11/7/2023
 */

public class JwtParsingException extends AuthenticationException {

    public JwtParsingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
