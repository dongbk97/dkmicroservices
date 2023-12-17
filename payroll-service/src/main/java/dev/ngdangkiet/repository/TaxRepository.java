package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.TaxEntity;
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
public interface TaxRepository extends JpaRepository<TaxEntity, Long> {

    @Query(nativeQuery = true, value =
            "with latestTaxes as ( " +
                    "select employee_id, tax_type, max(effective_date) as latest_effectiveDate " +
                    "from tbl_tax " +
                    "group by employee_id, tax_type) " +
                    "select t.* from tbl_tax as t " +
                    "inner join latestTaxes lt on lt.employee_id = t.employee_id " +
                    "and lt.latest_effectiveDate = t.effective_date " +
                    "and lt.tax_type = t.tax_type " +
                    "where t.employee_id in (:employeeIds)"
    )
    List<TaxEntity> findLatestTaxesByEmployeeIds(Collection<Long> employeeIds);
}
