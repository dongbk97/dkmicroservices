package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.PayrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 12/16/2023
 */

@Repository
public interface PayrollRepository extends JpaRepository<PayrollEntity, Long> {

    @Query("select p from PayrollEntity p " +
            "where (:employeeId = -1 or p.employeeId = :employeeId) " +
            "and (:month = -1 or extract(month from p.payDate) = :month) " +
            "and (:year = -1 or extract(year from p.payDate) = :year)")
    List<PayrollEntity> findPayrollByEmployeeIdAndYearMonth(Long employeeId, Integer month, Integer year);
}
