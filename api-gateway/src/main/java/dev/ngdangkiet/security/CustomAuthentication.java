package dev.ngdangkiet.security;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * @author ngdangkiet
 * @since 11/15/2023
 */

public class CustomAuthentication extends AbstractAuthenticationToken {

    private final PLoginResponse loginResponse;

    public CustomAuthentication(PLoginResponse loginResponse, Collection<? extends GrantedAuthority> authorities) {
        super(CollectionUtils.isEmpty(authorities) ? Collections.emptyList() : authorities);
        this.loginResponse = loginResponse;
        this.setDetails(loginResponse);
        this.setAuthenticated(loginResponse != PLoginResponse.getDefaultInstance());
    }

    @Override
    public Object getCredentials() {
        return this.loginResponse.getToken();
    }

    @Override
    public Object getPrincipal() {
        return this.loginResponse;
    }
}
