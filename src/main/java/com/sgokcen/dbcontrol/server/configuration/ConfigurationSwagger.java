package com.sgokcen.dbcontrol.server.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sgokcen.dbcontrol.server.controller.rest.v1.UserRestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfigurationSwagger {

    private ApiInfo apiInfo;

    public ConfigurationSwagger() {
    }

    private static final Set<String> PRODUCES_AND_CONSUMES_SET = new HashSet<>(Arrays.asList("application/json", "application/xml"));

    private static final String author = "Serdar Gokcen";
    private static final String authorWebAddress = "https://www.promising.ai/aboutus/sg";
    private static final String authorEmailAddress = "sg@promising.ai";
    private static final String title = "DBOPs";
    private static final String description = "DBOPs API Documentation";
    private static final String version = "1.0";
    private static final String tosUrl = "https://dbops.promising.ai/tos";
    private static final String license = "GPL v3";
    private static final String licenseUrl = "https://dbops.promising.ai/license";


    public ApiInfo getApiInfo() {
        if (apiInfo == null) {
            @SuppressWarnings("rawtypes")
            Collection<VendorExtension> vendorExtensions = new ArrayList<>();

            Contact contact = new Contact(author, authorWebAddress, authorEmailAddress);
            apiInfo = new ApiInfo(title, description, version, tosUrl, contact, license, licenseUrl, vendorExtensions);
        }
        return apiInfo;
    }

    @Bean
    public Docket apiInfo() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(UserRestController.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .consumes(PRODUCES_AND_CONSUMES_SET)
                .produces(PRODUCES_AND_CONSUMES_SET);
    }

}
