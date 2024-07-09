package com.TransportationService.service;

import com.TransportationService.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(int id);

    Customer updateCustomer(Customer customer);

    void deleteCustomerById(int id);
}
