package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.AttendanceRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecordEntity, Long> {

    Optional<AttendanceRecordEntity> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate now);
}
