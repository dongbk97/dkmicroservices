package dev.ngdangkiet.service;

import com.google.protobuf.Int64Value;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PDeduction;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsRequest;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PGetPayrollsResponse;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PSalary;
import dev.ngdangkiet.dkmicroservices.payroll.protobuf.PTax;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

public interface PayrollService {

    Int64Value upsertSalary(PSalary request);

    Int64Value upsertDeduction(PDeduction request);

    Int64Value upsertTax(PTax request);

    PGetPayrollsResponse getPayrolls(PGetPayrollsRequest request);
}
