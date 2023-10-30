package dev.ngdangkiet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ngdangkiet
 * @since 10/30/2023
 */

@SpringBootApplication
@EnableDiscoveryClient
public class EmployeeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class);
        System.out.println("Employee Service running!");
    }
}