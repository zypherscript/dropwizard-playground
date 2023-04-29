package com.example.demo.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;

@Path("")
public class HttpBinController {

  private final Client jerseyClient;

  public HttpBinController(Client jerseyClient) {
    this.jerseyClient = jerseyClient;
  }

  @GET
  @Path("/html")
  public String html() {
    var webTarget = jerseyClient.target("https://httpbin.org/html");
    Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
    var response = invocationBuilder.get();
    return response.readEntity(String.class);
  }
}
