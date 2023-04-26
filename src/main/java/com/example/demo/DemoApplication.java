package com.example.demo;

import com.example.demo.auth.AppAuthorizer;
import com.example.demo.auth.AppBasicAuthenticator;
import com.example.demo.auth.Template;
import com.example.demo.auth.User;
import com.example.demo.config.ApplicationConfiguration;
import com.example.demo.config.ApplicationHealthCheck;
import com.example.demo.controller.CustomerController;
import com.example.demo.controller.HealthCheckController;
import com.example.demo.repository.CustomerRepository;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.ws.rs.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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

    log.info("Registering REST resources");
    e.jersey().register(new CustomerController(e.getValidator(), new CustomerRepository()));

    final Template template = c.buildTemplate();
    log.info("Registering Application Health Check");
    e.healthChecks().register("template", new ApplicationHealthCheck(template));
//    e.healthChecks().runHealthChecks().forEach((key, value) -> log.info(key + " :: " + value));
    e.jersey().register(new HealthCheckController(e.healthChecks()));

    e.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
        .setAuthenticator(new AppBasicAuthenticator())
        .setAuthorizer(new AppAuthorizer())
        .setRealm("BASIC-AUTH-REALM")
        .buildAuthFilter()));
    e.jersey().register(RolesAllowedDynamicFeature.class);
    e.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
  }

  public static void main(String[] args) throws Exception {
    new DemoApplication().run(args);
  }
}
