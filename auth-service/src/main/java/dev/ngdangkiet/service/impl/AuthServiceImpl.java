package dev.ngdangkiet.service.impl;

import dev.ngdangkiet.constant.RedisCacheKeyConstant;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginRequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.encoder.PBKDF2Encoder;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.jwt.JwtUtil;
import dev.ngdangkiet.mapper.UserInfoMapper;
import dev.ngdangkiet.redis.RedisConfig;
import dev.ngdangkiet.service.AuthService;
import dev.ngdangkiet.service.RefreshTokenService;
import dev.ngdangkiet.utils.TwoFactorAuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final EmployeeService employeeService;
    private final RefreshTokenService refreshTokenService;
    private final TwoFactorAuthUtil twoFactorAuthUtil;
    private final PBKDF2Encoder pbkdf2Encoder;
    private final JwtUtil jwtUtil;
    private final RedisConfig redisConfig;
    private final UserInfoMapper userInfoMapper = UserInfoMapper.INSTANCE;

    @Override
    public PLoginResponse authentication(PLoginRequest request) {
        PLoginResponse.Builder builder = PLoginResponse.newBuilder()
                .setCode(ErrorCode.FAILED);
        try {
            Mono<EmployeeEntity> employee = employeeService.loadUserByUsername(request.getEmail());
            return employee.filter(userDetails ->
                            pbkdf2Encoder.encode(request.getPassword()).equals(userDetails.getPassword()) // by email and password
                                    || (request.getRequiredOtp() && validOtp(userDetails.getId(), request.getOtp())) // by email and otp from email
                                    || (request.getTwoFactorAuthentication() && Boolean.TRUE.equals(userDetails.getEnable2FA()) && twoFactorAuthUtil.isOtp2FAValid(userDetails.getSecret(), request.getCode())) /* by email and code from 2FA*/)
                    .map(userDetails -> builder.setCode(ErrorCode.SUCCESS)
                            .setToken(jwtUtil.generateToken(userDetails))
                            .setTokenUUID(refreshTokenService.createAndGetRefreshToken(userDetails.getId()))
                            .setUserInfo(userInfoMapper.toProtobuf(userDetails))
                            .build())
                    .switchIfEmpty(Mono.just(builder.build())).block();
        } catch (Exception e) {
            log.error("Username or password invalid!");
            e.printStackTrace();
            return builder.build();
        }
    }

    private boolean validOtp(Long employeeId, String otpRequest) {
        String otpCache = redisConfig.get(String.format(RedisCacheKeyConstant.Auth.USER_OTP_KEY, employeeId), String.class);
        return Objects.nonNull(otpCache) && otpCache.equals(otpRequest);
    }
}
