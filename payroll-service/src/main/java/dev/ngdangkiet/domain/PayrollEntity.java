package dev.ngdangkiet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_payroll")
public class PayrollEntity extends BaseEntity {

    private Long employeeId;

    private BigDecimal grossPay; // before deductions and taxes;

    private BigDecimal netPay; // after deductions and taxes;

    private String deductionsJson;

    private String taxesJson;

    private LocalDateTime payDate;
}
