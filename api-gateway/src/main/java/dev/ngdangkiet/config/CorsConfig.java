package dev.ngdangkiet.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/7/2023
 */

@Configuration
@EnableWebFlux
public class CorsConfig implements WebFluxConfigurer {

    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("#{'${cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;
    @Value("#{'${cors.allowed-headers}'.split(',')}")
    private List<String> allowedHeaders;
    @Value("#{'${cors.exposed-headers}'.split(',')}")
    private List<String> expectedHeaders;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(String.valueOf(allowedOrigins))
                .allowedMethods(String.valueOf(allowedMethods))
                .allowedHeaders(String.valueOf(allowedHeaders))
                .exposedHeaders(String.valueOf(expectedHeaders));
    }

}
