package dev.ngdangkiet.repository.impl;

import dev.ngdangkiet.domain.DTO.SearchHolidayDTO;
import dev.ngdangkiet.domain.HolidayEntity;
import dev.ngdangkiet.repository.HolidayRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class HolidayRepositoryCustomImpl implements HolidayRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<HolidayEntity> findByConditions(SearchHolidayDTO searchHoliday) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HolidayEntity> query = builder.createQuery(HolidayEntity.class);
        Root<HolidayEntity> holiday = query.from(HolidayEntity.class);
        query = query.select(holiday);

        if (Objects.nonNull(searchHoliday.getDateFrom())) {
            query.where(builder.lessThanOrEqualTo(holiday.get("date"), searchHoliday.getDateFrom()));
        }

        if (Objects.nonNull(searchHoliday.getDateTo())) {
            query.where(builder.greaterThanOrEqualTo(holiday.get("date"), searchHoliday.getDateTo()));
        }

        if (Objects.nonNull(searchHoliday.getName())) {
            query.where(builder.like(holiday.get("name"), searchHoliday.getName()));
        }

        if (Objects.nonNull(searchHoliday.getType())) {
            query.where(builder.like(holiday.get("type"), searchHoliday.getType()));
        }

        if (Objects.nonNull(searchHoliday.getYear())) {
            query.where(builder.equal(holiday.get("date_year"), searchHoliday.getYear()));
        }

        if (Objects.nonNull(searchHoliday.getMonth())) {
            query.where(builder.equal(holiday.get("date_month"), searchHoliday.getMonth()));
        }

        return entityManager.createQuery(query).getResultList();
    }
}
