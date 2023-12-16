package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.DTO.SearchHolidayDTO;
import dev.ngdangkiet.domain.HolidayEntity;

import java.util.List;

public interface HolidayRepositoryCustom {

    List<HolidayEntity> findByConditions(SearchHolidayDTO searchHoliday);
}
