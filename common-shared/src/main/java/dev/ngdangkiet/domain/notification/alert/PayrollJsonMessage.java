package dev.ngdangkiet.domain.notification.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class PayrollJsonMessage implements Serializable {

    private List<PayrollOfEachEmployee> payrollOfEachEmployees;
    private String notificationType;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "set", builderClassName = "newBuilder")
    public static class PayrollOfEachEmployee {
        private Long id;
        private Long employeeId;
        private Integer month;
        private Integer year;
        private String payDate;
        private BigDecimal grossPay;
        private BigDecimal netPay;
        private DeductionObj deductionObj;
        private TaxObj taxObj;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(setterPrefix = "set", builderClassName = "newBuilder")
        public static class DeductionObj {
            private List<Deduction> deductions;
            private BigDecimal totalDeductions;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder(setterPrefix = "set", builderClassName = "newBuilder")
        public static class TaxObj {
            private List<Tax> taxes;
            private BigDecimal totalTaxes;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "set", builderClassName = "newBuilder")
    public static class Deduction {
        private String type;
        private Double rate;
        private BigDecimal deduction;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "set", builderClassName = "newBuilder")
    public static class Tax {
        private String type;
        private Double rate;
        private BigDecimal tax;
    }
}
