package com.example.demo;

import com.example.demo.config.ApplicationConfiguration;
import com.example.demo.controller.CustomerController;
import com.example.demo.repository.CustomerRepository;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoApplication extends Application<ApplicationConfiguration> {

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> b) {
  }

  @Override
  public void run(ApplicationConfiguration c, Environment e) throws Exception {
    // TODO: client fetcher
//    log.info("Registering Jersey Client");
//    final Client client = new JerseyClientBuilder(e)
//        .using(c.getJerseyClientConfiguration())
//        .build(getName());
//    e.jersey().register(new APIController(client));

    log.info("Registering REST resources"); //e.getValidator(),
    e.jersey().register(new CustomerController(new CustomerRepository()));

    // TODO: healcheck & auth
  }

  public static void main(String[] args) throws Exception {
    new DemoApplication().run(args);
  }
}
