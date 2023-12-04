package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.AttendanceRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 12/2/2023
 */

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecordEntity, Long> {

    Optional<AttendanceRecordEntity> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate now);

    @Query("select ar from AttendanceRecordEntity ar " +
            "where ar.employeeId = :employeeId " +
            "and extract(year from ar.attendanceDate) = :year " +
            "and extract(month from ar.attendanceDate) = :month")
    List<AttendanceRecordEntity> findByEmployeeIdAndYearAndMonth(Long employeeId, Integer year, Integer month);
}
