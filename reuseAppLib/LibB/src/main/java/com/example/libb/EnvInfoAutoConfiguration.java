package com.example.libb;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@AutoConfiguration
public class EnvInfoAutoConfiguration {

  @Bean
  public EnvInfoService envInfoService(Environment env) {
    return new EnvInfoService(env);
  }
}
