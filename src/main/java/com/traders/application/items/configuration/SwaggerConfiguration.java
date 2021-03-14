package com.traders.application.items.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration{

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(basePackage("com.traders.application.items.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails());
    }

    public static ApiInfo apiDetails(){
        return new ApiInfoBuilder()
                .title("Trader API")
                .description("Online Trader Application with Currency Conversion")
                .version("1.0")
                .contact(new Contact("Syed Mohammad","","mousvi.86@gmail.com"))
                .license("API License")
                .build();
    }
}
