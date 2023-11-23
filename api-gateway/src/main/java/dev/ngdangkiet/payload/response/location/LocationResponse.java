package dev.ngdangkiet.payload.response.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse {
    String address;
    Double longitude;
    Double latitude;
    String fromLocation;
    String toLocation;
//    @Getter
//    @Setter
//    @Builder
//    public static class Location {
//        String address;
//        Double longitude;
//        Double latitude;
//    }
}
