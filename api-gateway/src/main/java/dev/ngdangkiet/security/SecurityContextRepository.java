package dev.ngdangkiet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 11/8/2023
 */

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(securityProperties.getHeaderName()))
                .filter(header -> header.startsWith(securityProperties.getHeaderPrefix()))
                .flatMap(header -> {
                    String token = StringUtils.replace(header, securityProperties.getHeaderPrefix(), EMPTY);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);
                    return authenticationManager.authenticate(authentication).map(SecurityContextImpl::new);
                });
    }
}
