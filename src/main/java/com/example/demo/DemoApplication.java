package com.example.demo;

import com.example.demo.config.ApplicationConfiguration;
import com.example.demo.config.ApplicationHealthCheck;
import com.example.demo.controller.CustomerController;
import com.example.demo.repository.CustomerRepository;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.ws.rs.client.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoApplication extends Application<ApplicationConfiguration> {

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> b) {
  }

  @Override
  public void run(ApplicationConfiguration c, Environment e) {
    log.info("Registering Jersey Client");
    final Client client = new JerseyClientBuilder(e)
        .using(c.getJerseyClientConfiguration())
        .build(getName());
//    e.jersey().register(new APIController(client));

    log.info("Registering REST resources"); //e.getValidator(),
    e.jersey().register(new CustomerController(new CustomerRepository()));

    log.info("Registering Application Health Check");
    e.healthChecks().register("application", new ApplicationHealthCheck(client));

    // TODO: auth
  }

  public static void main(String[] args) throws Exception {
    new DemoApplication().run(args);
  }
}
