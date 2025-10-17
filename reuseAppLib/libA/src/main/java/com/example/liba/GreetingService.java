package com.example.liba;

public class GreetingService {
  private final GreetingProperties props;

  public GreetingService(GreetingProperties props) {
    this.props = props;
  }

  public String greet(String name) {
    return String.format(props.getTemplate(), name);
  }
}
