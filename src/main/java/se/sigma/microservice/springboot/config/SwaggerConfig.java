package se.sigma.microservice.springboot.config;

import java.util.Collections;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"dev", "test", "default"})
@NoArgsConstructor
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("se.sigma.microservice.springboot.controller"))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("")
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false);
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "spring-boot-example",
        "An example Spring Boot microservice",
        "0.1",
        "",
        new Contact(
            "Chris Sundberg", "https://github.com/chriskevin", "chris.sundberg@protonmail.com"),
        "MIT",
        "https://github.com/chriskevin/microservice-java-spring-boot/blob/master/LICENSE",
        Collections.emptyList());
  }
}
