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

}
