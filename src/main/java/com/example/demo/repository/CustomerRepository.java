package com.example.demo.repository;

import com.example.demo.entity.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerRepository {

  public static HashMap<Integer, Customer> customers = new HashMap<>();

  static {
    customers.put(1, new Customer(1, "test", "test@example.com"));
    customers.put(2, new Customer(2, "test2", "test2@example.com"));
  }

  public List<Customer> getCustomers() {
    return new ArrayList<>(customers.values());
  }

  public Customer getCustomer(Integer id) {
    return customers.get(id);
  }

//  public static void updateCustomer(Integer id, Customer customer) {
//    customers.put(id, customer);
//  }
//
//  public static void removeCustomer(Integer id) {
//    customers.remove(id);
//  }
}
