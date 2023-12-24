package dev.ngdangkiet.domain;

import dev.ngdangkiet.enums.payroll.PayPeriod;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "tbl_salary")
public class SalaryEntity extends BaseEntity {

    private Long employeeId;

    private BigDecimal baseSalary;

    @Enumerated(EnumType.STRING)
    private PayPeriod payPeriod;

    private LocalDate effectiveDate;
}
