package dev.ngdangkiet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LocationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LocationServiceApplication.class, args);
//        List<Double> location = getCoordinate("172 Võ Thị Sáu, Phường 6, Quận 3, Thành phố Hồ Chí Minh, Vietnam");
//        System.out.println(location.get(0));
//        System.out.println(location.get(1));
//        List<String> places = getSameNameLocation("12 Tôn đản");
//        places.forEach(System.out::println);
//        calculateDistanceFrom2Point(null, null,null);
    }
}