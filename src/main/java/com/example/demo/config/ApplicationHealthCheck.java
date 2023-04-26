package com.example.demo.config;

import com.codahale.metrics.health.HealthCheck;
import com.example.demo.auth.Template;
import java.util.Optional;

public class ApplicationHealthCheck extends HealthCheck {

  private final Template template;

  public ApplicationHealthCheck(Template template) {
    this.template = template;
  }

  @Override
  protected Result check() {
    template.render(Optional.of("meet the woo"));
    template.render(Optional.empty());
    return Result.healthy();
  }
}
