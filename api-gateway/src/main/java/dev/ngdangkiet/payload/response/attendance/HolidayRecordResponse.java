package dev.ngdangkiet.payload.response.attendance;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayRecordResponse {
    private Long id;
    private String name;
    private String type;
    private String date;
    private Integer date_year;
    private Integer date_month;
    private Integer date_day;
    private String week_day;
}