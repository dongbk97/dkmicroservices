package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.DeductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Repository
public interface DeductionRepository extends JpaRepository<DeductionEntity, Long> {

    @Query(nativeQuery = true, value =
            "with latestDeductions as ( " +
                    "select employee_id, deduction_type, max(effective_date) as latest_effectiveDate " +
                    "from tbl_deduction " +
                    "group by employee_id, deduction_type) " +
                    "select d.* from tbl_deduction as d " +
                    "inner join latestDeductions ld on ld.employee_id = d.employee_id " +
                    "and ld.latest_effectiveDate = d.effective_date " +
                    "and ld.deduction_type = d.deduction_type " +
                    "where d.employee_id in (:employeeIds)"
    )
    List<DeductionEntity> findLatestDeductionsByEmployeeIds(Collection<Long> employeeIds);
}
