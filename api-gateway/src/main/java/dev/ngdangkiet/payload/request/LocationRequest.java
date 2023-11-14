package dev.ngdangkiet.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {
    String address;
    Double longitude;
    Double latitude;
}
