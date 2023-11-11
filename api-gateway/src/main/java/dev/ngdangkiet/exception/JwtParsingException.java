package dev.ngdangkiet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ngdangkiet
 * @since 11/7/2023
 */

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtParsingException extends AuthenticationException {

    public JwtParsingException(String msg) {
        super(msg);
    }
}
