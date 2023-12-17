package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.SalaryEntity;
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
public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {

    @Query(nativeQuery = true, value =
            "with latestSalaries as ( " +
                    "select employee_id, max(effective_date) as latest_effectiveDate " +
                    "from tbl_salary " +
                    "group by employee_id) " +
                    "select s.* from tbl_salary as s " +
                    "inner join latestSalaries ls on ls.employee_id = s.employee_id " +
                    "and ls.latest_effectiveDate = s.effective_date " +
                    "where s.employee_id in (:employeeIds)"
    )
    List<SalaryEntity> findLatestSalariesByEmployeeIds(Collection<Long> employeeIds);
}
