package com.pyruz.shortening.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Optional;

@Configuration
@EnableSwagger2
@EnableTransactionManagement
@EnableJpaAuditing
public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(Predicates.not(PathSelectors.regex("/error")))
        .build()
        .apiInfo(metadata())
        .useDefaultResponseMessages(false)
        .genericModelSubstitutes(Optional.class);
  }

  private ApiInfo metadata() {
    return new ApiInfoBuilder()
        .title("URL shortening Service")
        .version("1.0.0")
        .licenseUrl("https://www.linkedin.com/in/pyruz-janbaaz-9a327226/")
        .contact(
            new Contact(
                "Pyruz Janbaaz",
                "https://www.linkedin.com/in/pyruz-janbaaz-9a327226/",
                "Pyruz.Janbaaz@gmail.com"))
        .build();
  }
}
