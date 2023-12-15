package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {
    @Query("SELECT h FROM HolidayEntity h WHERE h.date IN (:dateToValidate) ")
    List<HolidayEntity> findByDates(Set<LocalDate> dateToValidate);

    @Query("SELECT h FROM HolidayEntity h " +
            "WHERE (:dateFrom IS NULL OR h.date >= :dateFrom) " +
            "AND (:dateTo IS NULL OR h.date <= :dateTo) " +
            "AND (:name IS NULL OR h.name LIKE %:name%) " +
            "AND (:type IS NULL OR h.type LIKE %:type%)" +
            "AND (:year IS NULL OR h.date_year = :year) " +
            "AND (:month IS NULL OR h.date_month = :month)")
    List<HolidayEntity> findByConditions(
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            @Param("name") String name,
            @Param("type") String type,
            @Param("year") Integer year,
            @Param("month") Integer month);
}
