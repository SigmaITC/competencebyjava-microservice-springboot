package se.sigma.microservice.springboot.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper =
        Jackson2ObjectMapperBuilder.json()
            .featuresToEnable(MapperFeature.DEFAULT_VIEW_INCLUSION)
            .build();

    // Some other custom configuration for supporting Java 8 features
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.registerModule(new VavrModule());

    // Use property
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

    return objectMapper;
  }
}
