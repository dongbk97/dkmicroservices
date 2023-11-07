package dev.ngdangkiet.config;

import dev.ngdangkiet.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/5/2023
 */

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BearerAuthenticationReader(token -> {
            Claims claims = jwtUtil.claims(token);
            List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            User user = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(user, token, authorities);
        });
    }
}
