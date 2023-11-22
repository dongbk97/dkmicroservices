package dev.ngdangkiet.elasticsearch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ngdangkiet
 * @since 11/11/2023
 */

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(
                        List.of(new Server().url("http://localhost:8003").description("Development environment"))
                )
                .info(new Info()
                        .title("DKMicroservices Open API")
                        .description("DKMicroservices Description...")
                        .version("1.0")
                        .contact(new Contact()
                                .url("https://github.com/ngdangkiet")
                                .name("Nguyen Dang Kiet")
                                .email("kiet.nguyen-dang@banvien.com.vn")
                        )
                );
    }
}
