package com.prueba.crud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API CRUD Clientes")
                        .description(
                                "API REST para gestion de Clientes y Estados. " +
                                        "Java 17 + Spring Boot + Hibernate (config XML) " +
                                        "+ Apache Tomcat.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Marcos Sandoval")
                                .email("tu@email.com")));
    }
}