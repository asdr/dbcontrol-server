package com.sgokcen.dbcontrol.server.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationOpenAPI {

    public ConfigurationOpenAPI() {
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DbControl Server")
                        .description("DbControl API Documentation")
                        .version("v1.0.0")
                        .license(new License().name("LGPLv3").url("https://www.gnu.org/licenses/lgpl-3.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("DbControl Documentations")
                        .url("https://dbcontrol.sgokcen.com/docs"));
    }
}
