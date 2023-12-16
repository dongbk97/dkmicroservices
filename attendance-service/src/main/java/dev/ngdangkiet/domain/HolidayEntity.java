package dev.ngdangkiet.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
@Entity
@Table(name = "tbl_holidays")
public class HolidayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    private LocalDate date;

    private Integer date_year;
    private Integer date_month;
    private Integer date_day;
    private String week_day;

}
