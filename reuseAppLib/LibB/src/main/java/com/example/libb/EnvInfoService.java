package com.example.libb;

import org.springframework.core.env.Environment;

public class EnvInfoService {
  private final Environment env;

  public EnvInfoService(Environment env) {
    this.env = env;
  }

  public EnvInfo current() {
    String appName = env.getProperty("spring.application.name", "main-app");
    String[] profiles = env.getActiveProfiles();
    String profile = (profiles == null || profiles.length == 0) ? "default" : String.join(",", profiles);
    String javaVersion = System.getProperty("java.version");
    return new EnvInfo(appName, profile, javaVersion);
  }
}
