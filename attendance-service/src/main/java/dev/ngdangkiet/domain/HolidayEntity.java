package dev.ngdangkiet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_holidays")
public class HolidayEntity extends BaseEntity {

    private String name;

    private String type;

    private LocalDate date;

    private Integer date_year;

    private Integer date_month;

    private Integer date_day;

    private String week_day;

}
