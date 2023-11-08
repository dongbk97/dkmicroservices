package dev.ngdangkiet.service;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginRequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.encoder.PBKDF2Encoder;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.jwt.JwtUtil;
import dev.ngdangkiet.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final EmployeeService employeeService;
    private final PBKDF2Encoder pbkdf2Encoder;
    private final JwtUtil jwtUtil;
    private final UserInfoMapper userInfoMapper = UserInfoMapper.INSTANCE;

    @Override
    public PLoginResponse authentication(PLoginRequest request) {
        PLoginResponse.Builder builder = PLoginResponse.newBuilder()
                .setCode(ErrorCode.FAILED);
        try {
            Mono<EmployeeEntity> employee = employeeService.loadUserByUsername(request.getEmail());
            return employee.filter(userDetails -> pbkdf2Encoder.encode(request.getPassword()).equals(userDetails.getPassword()))
                    .map(userDetails -> builder.setCode(ErrorCode.SUCCESS)
                            .setToken(jwtUtil.generateToken(userDetails))
                            .setUserInfo(userInfoMapper.toProtobuf(userDetails))
                            .build())
                    .switchIfEmpty(Mono.just(builder.build())).block();
        } catch (Exception e) {
            log.error("Username or password invalid!");
            e.printStackTrace();
            return builder.build();
        }
    }
}
