package br.gov.prf.silver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("silver")
            .securitySchemes(Collections.singletonList(new ApiKey("JWT", "Authorization", "header")))
            .securityContexts(Collections.singletonList(
                SecurityContext.builder()
                    .securityReferences(
                        Collections.singletonList(SecurityReference.builder()
                            .reference("JWT")
                            .scopes(new AuthorizationScope[0])
                            .build()
                        )
                    )
                    .build())
            )
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(this.metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
            .title("Silver REST API")
            .description("Spring Boot REST API for SILVER")
            .version("1.0.0")
            .license("Apache License Version 2.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
            .contact(new Contact("Diasi - PRF", "https://www.prf.gov.br", "diasi@prf.gov.br"))
            .build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .docExpansion(DocExpansion.NONE) // or DocExpansion.NONE or DocExpansion.FULL
            .build();
    }
}
