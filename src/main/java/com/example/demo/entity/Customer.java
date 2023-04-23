package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

  @NotNull
  private Integer id; //@id?

  @NotBlank
  @Length(min = 2, max = 255)
  private String name;

  @Pattern(regexp = ".+@.+\\.[a-z]+")
  private String email;
}
