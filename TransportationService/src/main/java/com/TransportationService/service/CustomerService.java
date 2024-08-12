package com.TransportationService.service;

import com.TransportationService.dto.request.CustomerDto;
import com.TransportationService.dto.request.CustomerUpdateDto;
import com.TransportationService.dto.response.CustomerResponseDto;
import com.TransportationService.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(CustomerDto customerDto);

    List<Customer> getAllCustomers();

    CustomerResponseDto getCustomerById(int id);

    Customer updateCustomer(CustomerUpdateDto customerUpdateDto);

    void deleteCustomerById(int id);
}
