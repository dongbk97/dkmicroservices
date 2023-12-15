package dev.ngdangkiet.payload.request.attendance;


import java.io.Serializable;

public class SearchHolidaysRequest implements Serializable {
    private String dateFrom;
    private String dateTo;
    private String name;
    private String type;
    private Integer year;
    private Integer month;
}