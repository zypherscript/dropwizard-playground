package com.example.demo.auth;

import java.security.Principal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Principal {

  private final String name;
  private final Set<String> roles;

  public User(String name) {
    this.name = name;
    this.roles = null;
  }

  public int getId() {
    return (int) (Math.random() * 100);
  }
}