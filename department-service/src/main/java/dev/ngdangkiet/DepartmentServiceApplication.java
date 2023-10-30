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
public class DepartmentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentServiceApplication.class);
        System.out.println("Department Service running!");
    }
}