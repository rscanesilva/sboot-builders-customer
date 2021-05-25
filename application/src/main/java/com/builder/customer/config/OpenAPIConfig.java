package com.builder.customer.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .contact(new Contact()
                                .email("rscanesilva@gmail.com")
                                .url("https://linkedin/in/rscanesilva")
                                .name("Rafael Scane"))
                        .description("Project for Builder's Tech Lead job")
                        .title("Customer API")
                )
                .components(new Components());
    }
}