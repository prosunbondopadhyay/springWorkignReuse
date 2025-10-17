package com.example.liba;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(GreetingController.class)
@EnableConfigurationProperties(GreetingProperties.class)
public class GreetingAutoConfiguration {

  @Bean
  public GreetingService greetingService(GreetingProperties props) {
    return new GreetingService(props);
  }
}
