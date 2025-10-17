package com.example.liba;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(prefix = "greeting", name = "web-enabled", havingValue = "true", matchIfMissing = true)
public class GreetingController {
  private final GreetingService service;

  GreetingController(GreetingService service) {
    this.service = service;
  }

  @GetMapping("/greet")
  public String greet(@RequestParam(name = "name", required = false, defaultValue = "world") String name) {
    return service.greet(name);
  }
}
