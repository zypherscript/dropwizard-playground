package com.example.demo.controller;

import com.example.demo.auth.User;
import com.example.demo.repository.CustomerRepository;
import io.dropwizard.auth.Auth;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Validator;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CustomerController {

  private final Validator validator;
  private final CustomerRepository customerRepository;

  @GET
  @PermitAll
  public Response getCustomers(@Auth User user) {
    return Response.ok(customerRepository.getCustomers()).build();
  }

  @GET
  @Path("/{id}")
  @PermitAll
  public Response getCustomerById(@PathParam("id") Integer id, @Auth User user) {
    var customer = customerRepository.getCustomer(id);
    if (customer != null) {
      return Response.ok(customer).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  // TODO: more endpoints for CRUD
}
