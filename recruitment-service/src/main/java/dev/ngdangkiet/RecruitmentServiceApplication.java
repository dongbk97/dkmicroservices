package dev.ngdangkiet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ngdangkiet
 * @since 11/27/2023
 */

@SpringBootApplication
public class RecruitmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentServiceApplication.class, args);
        System.out.println("Recruitment Service running!");
    }
}