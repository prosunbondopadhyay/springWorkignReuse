package com.example.main;

import com.example.libb.EnvInfo;
import com.example.libb.EnvInfoService;
import com.example.liba.GreetingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MainSpringApp {
  public static void main(String[] args) {
    SpringApplication.run(MainSpringApp.class, args);
  }

  @RestController
  static class DemoController {
    private final GreetingService greeting;
    private final EnvInfoService envInfo;

    DemoController(GreetingService greeting, EnvInfoService envInfo) {
      this.greeting = greeting;
      this.envInfo = envInfo;
    }

    @GetMapping("/demo")
    public String demo() {
      EnvInfo info = envInfo.current();
      return greeting.greet(info.appName() + " (" + info.profile() + ", Java " + info.javaVersion() + ")");
    }
  }
}
