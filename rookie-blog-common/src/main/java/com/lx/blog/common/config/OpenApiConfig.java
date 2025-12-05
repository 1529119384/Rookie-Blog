package com.lx.blog.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${sa-token.token-name}")
    private String tokenName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Blog API")
                        .version("v1")
                        .description("博客后端接口"))
                .components(new Components()
                        .addSecuritySchemes(tokenName, new SecurityScheme()
                                .name(tokenName)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)))
                .addSecurityItem(new SecurityRequirement().addList(tokenName));
    }
}

