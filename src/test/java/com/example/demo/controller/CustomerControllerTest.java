package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class CustomerControllerTest {

  private static final CustomerRepository DAO = mock(CustomerRepository.class);
  private static final Validator validator = mock(Validator.class);
  private static final ResourceExtension EXT = ResourceExtension.builder()
      .addResource(new CustomerController(validator, DAO))
      .build();
  private Customer customer;

  @BeforeEach
  void setup() {
    customer = new Customer();
    customer.setId(1);
    customer.setName("name");
    customer.setEmail("email@example.com");
  }

  @AfterEach
  void tearDown() {
    reset(DAO);
  }

  @Test
  void getCustomers() {
    when(DAO.getCustomers()).thenReturn(List.of(customer));

    var response = EXT.target("/customers").request().get();
    var found = response.readEntity(new GenericType<List<Customer>>() {
    });

    assertThat(found).isEqualTo(List.of(customer));
    verify(DAO).getCustomers();
  }

  @Test
  void getCustomerSuccess() {
    when(DAO.getCustomer(1)).thenReturn(customer);

    var found = EXT.target("/customers/1").request().get(Customer.class);

    assertThat(found).isEqualTo(customer);
    verify(DAO).getCustomer(1);
  }

  @Test
  void getCustomerNotFound() {
    when(DAO.getCustomer(2)).thenReturn(null);
    final Response response = EXT.target("/customers/2").request().get();

    assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(
        Response.Status.NOT_FOUND.getStatusCode());
    verify(DAO).getCustomer(2);
  }
}