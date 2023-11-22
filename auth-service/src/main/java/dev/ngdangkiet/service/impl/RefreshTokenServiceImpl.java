package dev.ngdangkiet.service.impl;

import com.google.protobuf.StringValue;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PRefreshTokenResponse;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.domain.RefreshTokenEntity;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.jwt.JwtUtil;
import dev.ngdangkiet.repository.EmployeeRepository;
import dev.ngdangkiet.repository.RefreshTokenRepository;
import dev.ngdangkiet.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * @author ngdangkiet
 * @since 11/22/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final EmployeeRepository employeeRepository;
    private final JwtUtil jwtUtil;
    @Value("${security.auth.expiration-time}")
    private Long expirationTime;

    @Override
    public String createAndGetRefreshToken(Long userId) {
        EmployeeEntity employee = employeeRepository.findById(userId).orElse(null);
        if (Objects.isNull(employee)) {
            log.error("Not found userId [{}]", userId);
            return EMPTY;
        }
        try {
            RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                    .setUser(employee)
                    .setTokenUUID(UUID.randomUUID().toString())
                    .setExpirationDate(new Date(new Date().getTime() + expirationTime * 1000))
                    .build();

            return refreshTokenRepository.save(refreshToken).getTokenUUID();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error create and get refresh token!. Error [{}]", e.getMessage());
            return EMPTY;
        }
    }

    @Override
    public PRefreshTokenResponse refreshToken(StringValue tokenUUID) {
        PRefreshTokenResponse.Builder builder = PRefreshTokenResponse.newBuilder().setCode(ErrorCode.FAILED);

        RefreshTokenEntity refreshToken = refreshTokenRepository.findByTokenUUID(tokenUUID.getValue()).orElse(null);
        if (Objects.isNull(refreshToken)) {
            log.error("Not found refreshToken with tokenUUID [{}]", tokenUUID.getValue());
            return builder.setCode(ErrorCode.NOT_FOUND).build();
        }
        try {
            if (refreshToken.getExpirationDate().before(new Date())) {
                log.error("Refresh token with [{}] was expired. Please make a new sign in request", tokenUUID.getValue());
                refreshTokenRepository.delete(refreshToken);
            } else {
                builder.setCode(ErrorCode.SUCCESS).setToken(jwtUtil.generateToken(refreshToken.getUser()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error get refreshToken!. Error [{}]", e.getMessage());
        }
        return builder.build();
    }
}
