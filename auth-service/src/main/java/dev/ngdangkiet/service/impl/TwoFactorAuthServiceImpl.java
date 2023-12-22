package dev.ngdangkiet.service.impl;

import dev.ngdangkiet.dkmicroservices.auth.protobuf.PEnableOrDisable2FARequest;
import dev.ngdangkiet.dkmicroservices.auth.protobuf.PGenerateQRCodeResponse;
import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.error.ErrorCode;
import dev.ngdangkiet.repository.EmployeeRepository;
import dev.ngdangkiet.service.TwoFactorAuthService;
import dev.ngdangkiet.utils.TwoFactorAuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author ngdangkiet
 * @since 12/20/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class TwoFactorAuthServiceImpl implements TwoFactorAuthService {

    private final EmployeeRepository employeeRepository;
    private final TwoFactorAuthUtil twoFactorAuthUtil;

    @Override
    public PGenerateQRCodeResponse enableOrDisable2FA(PEnableOrDisable2FARequest request) {
        PGenerateQRCodeResponse.Builder builder = PGenerateQRCodeResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            EmployeeEntity employee = employeeRepository.findById(request.getUserId()).orElse(null);
            if (Objects.isNull(employee)) {
                log.error("Employee ID [{}] not found!", request.getUserId());
                return builder.setCode(ErrorCode.NOT_FOUND).build();
            }

            if (request.getEnable()) {
                if (!Boolean.TRUE.equals(employee.getEnable2FA())) {
                    employee.setEnable2FA(true);
                    employee.setSecret(twoFactorAuthUtil.generateNewSecret());
                }
            } else {
                employee.setEnable2FA(false);
                employee.setSecret(null);
            }

            employeeRepository.save(employee);

            return builder.setCode(ErrorCode.SUCCESS)
                    .setQrCodeImageUrl(twoFactorAuthUtil.generateQrCodeImageUrl(employee.getSecret()))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
