package com.example.demo.config;

import com.codahale.metrics.health.HealthCheck;
import com.example.demo.entity.Customer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

public class ApplicationHealthCheck extends HealthCheck {

  private final Client client;

  public ApplicationHealthCheck(Client client) {
    super();
    this.client = client;
  }

  @Override
  protected Result check() {
    var webTarget = client.target("http://localhost:8080/customers");
    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

    var response = invocationBuilder.get();
    var customers = response.readEntity(new GenericType<List<Customer>>() {
    });
    if (customers != null && !customers.isEmpty()) {
      System.out.println(customers);
      return Result.healthy();
    }
    return Result.unhealthy("API Failed");
  }
}
