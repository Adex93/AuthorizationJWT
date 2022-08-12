package com.example.authorizationjwt.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for swagger using OpenApi.<br/>
 * Notice the spring security must allow to access to the swagger ui at 'SecurityConfiguration.java'.<br/>
 * There are also configuration at 'application.properties' for defining the URL to swagger page.
 */
@Configuration
public class SwaggerConfig {

    public static final String SCHEME_NAME = "Authorization";
    public static final String SCHEME = "ApiKey";

    @Bean
    public OpenAPI customOpenAPI() {
        var openApi = new OpenAPI().info(this.apiInfo());
        this.addSecurity(openApi);
        return openApi;
    }

    private Info apiInfo() {
        var contact = new Contact();
        contact.setEmail("dmitriev_alexandr93@mail.ru");
        contact.setName("Dmitriev Aleksandr");

        return new Info()
                .title("Authorization API")
                .contact(contact);
    }

    private void addSecurity(OpenAPI openApi) {
        var components = this.createComponents();
        var securityItem = new SecurityRequirement().addList(SCHEME_NAME);
        openApi.components(components).addSecurityItem(securityItem);
    }

    private Components createComponents() {
        var components = new Components();
        components.addSecuritySchemes(SCHEME_NAME, this.createSecurityScheme());
        return components;
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme().name(SCHEME_NAME).type(SecurityScheme.Type.APIKEY).scheme(SCHEME).in(SecurityScheme.In.HEADER);
    }
}