package nl.ing.accounts.domain.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Account Management API")
                .description("API for managing accounts")
                .version("v1.0")
                .contact(new Contact().name("Support Team").email("vikrantcdac90@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                .description("Project Wiki Documentation")
                .url("http://localhost:8080/"));
    }

    @Bean
    public GroupedOpenApi publicApi() {

        return GroupedOpenApi.builder()
                .group("nl.ing.accounts")
                .pathsToMatch("/api/accounts/**")
                .build();
    }
}

