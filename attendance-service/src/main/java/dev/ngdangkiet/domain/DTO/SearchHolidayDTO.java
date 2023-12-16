package dev.ngdangkiet.domain.DTO;

import lombok.*;

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
