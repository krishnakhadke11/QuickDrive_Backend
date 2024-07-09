package com.TransportationService.service.impl;

import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Ride;
import com.TransportationService.repository.CustomerRepository;
import com.TransportationService.repository.RideRepository;
import com.TransportationService.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final RideRepository rideRepository;
    CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RideRepository rideRepository) {
        this.customerRepository = customerRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found!"));
        return customer;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if(!customerRepository.existsById(customer.getId())) {
            throw new EntityNotFoundException("Customer not found!");
        }
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerById(int id) {
        if(!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found!");
        }
        customerRepository.deleteById(id);
    }
}
