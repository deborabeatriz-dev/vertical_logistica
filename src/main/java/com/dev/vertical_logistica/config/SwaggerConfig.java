package com.dev.vertical_logistica.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vertical Logística API")
                        .version("1.0.0")
                        .description("API REST para processamento de arquivos de sistemas legados e gestão de usuários, pedidos e produtos")
                        .contact(new Contact()
                                .name("Débora Beatriz")
                                .email("deborabeatriz.dev@gmail.com")));
    }
}
