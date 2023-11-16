package dev.ngdangkiet.security;

import dev.ngdangkiet.client.EmployeeGrpcClient;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.dkmicroservices.employee.protobuf.PEmployee;
import dev.ngdangkiet.exception.JwtParsingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/8/2023
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;
    private final EmployeeGrpcClient employeeGrpcClient;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        try {
            String username = jwtUtil.getUsernameFromToken(token);
            return Mono.just(jwtUtil.getUsernameFromToken(token))
                    .switchIfEmpty(Mono.empty())
                    .map(valid -> {
                        Claims claims = jwtUtil.getAllClaimsFromToken(token);
                        List<String> authorities = claims.get("authorities", List.class);

                        PEmployee pEmployee = employeeGrpcClient.getEmployeeByEmail(username);
                        PLoginResponse pLoginResponse = PLoginResponse.newBuilder()
                                .setToken(token)
                                .setUserInfo(PLoginResponse.UserInfo.newBuilder()
                                        .setId(pEmployee.getId())
                                        .setEmail(pEmployee.getEmail())
                                        .setFullName(pEmployee.getFullName())
                                        .build())
                                .build();

                        CustomAuthentication customAuthentication = new CustomAuthentication(pLoginResponse, authorities.stream()
                                .map(SimpleGrantedAuthority::new).toList());
                        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

                        return customAuthentication;
                    });
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtParsingException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            log.error("JWT token is expired: {}", e.getMessage());
            throw new JwtParsingException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtParsingException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new JwtParsingException("JWT claims string is empty");
        }
    }
}
