package dev.ngdangkiet.security;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.mapper.response.LoginResponseMapper;
import dev.ngdangkiet.payload.response.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/15/2023
 */

@RequiredArgsConstructor
public class SecurityHelper {

    private static final LoginResponseMapper loginResponseMapper = LoginResponseMapper.INSTANCE;

    public static LoginResponse getUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)) {
            return loginResponseMapper.toDomain((PLoginResponse) authentication.getPrincipal());
        }

        return null;
    }

}
