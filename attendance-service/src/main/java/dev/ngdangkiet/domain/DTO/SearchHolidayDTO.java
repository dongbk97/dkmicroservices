package dev.ngdangkiet.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", builderClassName = "newBuilder")
public class SearchHolidayDTO {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String name;
    private String type;
    private Integer year;
    private Integer month;
}
