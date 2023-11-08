package dev.ngdangkiet.jwt;

import dev.ngdangkiet.domain.EmployeeEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Component
@Slf4j
public class JwtUtil {

    @Value("${security.auth.secret-key}")
    private String secretKey;

    @Value("${security.auth.expiration-time}")
    private Long expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(EmployeeEntity employee) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", List.of(employee.getPosition().getName()));
        return doGenerateToken(claims, employee.getEmail());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTime * 1000))
                .signWith(key)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
