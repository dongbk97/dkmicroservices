package dev.ngdangkiet.service.impl;

import com.google.protobuf.Int64Value;
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
    public PGenerateQRCodeResponse enable2FA(Int64Value employeeId) {
        PGenerateQRCodeResponse.Builder builder = PGenerateQRCodeResponse.newBuilder().setCode(ErrorCode.FAILED);

        try {
            EmployeeEntity employee = employeeRepository.findById(employeeId.getValue()).orElse(null);
            if (Objects.isNull(employee)) {
                log.error("Employee ID [{}] not found!", employeeId.getValue());
                return builder.setCode(ErrorCode.NOT_FOUND).build();
            }

            employee.setEnable2FA(true);
            employee.setSecret(twoFactorAuthUtil.generateNewSecret());
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
