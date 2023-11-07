package dev.ngdangkiet.service;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginRequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PLoginResponse;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public PLoginResponse authentication(PLoginRequest request) {
        PLoginResponse.Builder builder = PLoginResponse.newBuilder()
                .setCode(ErrorCode.FAILED);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            EmployeeEntity employee = (EmployeeEntity) authentication.getPrincipal();
            builder.setCode(ErrorCode.SUCCESS)
                    .setToken(jwtUtil.generateToken(authentication))
                    .setUserInfo(PLoginResponse.UserInfo.newBuilder()
                            .setId(employee.getId())
                            .setEmail(employee.getEmail())
                            .setFullName(employee.getFullName())
                            .build());
        } catch (Exception e) {
            log.error("Username or password invalid!");
            e.printStackTrace();
        }

        return builder.build();
    }
}
