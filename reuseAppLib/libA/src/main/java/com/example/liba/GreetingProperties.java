package com.example.liba;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "greeting")
public class GreetingProperties {
  /**
   * Default message template, e.g. "Hello, %s!"
   */
  private String template = "Hello, %s!";
  /**
   * Whether the built-in controller is exposed.
   */
  private boolean webEnabled = true;

  public String getTemplate() { return template; }
  public void setTemplate(String template) { this.template = template; }

  public boolean isWebEnabled() { return webEnabled; }
  public void setWebEnabled(boolean webEnabled) { this.webEnabled = webEnabled; }
}
