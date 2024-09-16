package com.TransportationService.service.impl;

import com.TransportationService.dto.request.CustomerDto;
import com.TransportationService.dto.request.CustomerUpdateDto;
import com.TransportationService.dto.response.CustomerResponseDto;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Role;
import com.TransportationService.entity.User;
import com.TransportationService.repository.CustomerRepository;
import com.TransportationService.repository.RideRepository;
import com.TransportationService.service.CustomerService;
import com.TransportationService.util.UserUtil;
import com.TransportationService.validation.CustomerValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final RideRepository rideRepository;
    private final CustomerValidation customerValidation;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RideRepository rideRepository, PasswordEncoder passwordEncoder, CustomerValidation customerValidation) {
        this.customerRepository = customerRepository;
        this.rideRepository = rideRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerValidation = customerValidation;
    }

    @Override
    public Customer addCustomer(CustomerDto customerDto) {
        String password = customerDto.getUser().getPassword();
        Customer customer = new Customer();

        User user = new User();
        user.setFirstName(customerDto.getUser().getFirstName());
        user.setLastName(customerDto.getUser().getLastName());
        user.setEmail(customerDto.getUser().getEmail());
        user.setPhoneNumber(customerDto.getUser().getPhoneNumber());
        user.setAddress(customerDto.getUser().getAddress());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.CUSTOMER);

        customer.setUser(user);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerResponseDto getCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found!"));
        CustomerResponseDto customerRes = new CustomerResponseDto();
        customerRes.setId(customer.getId());
        customerRes.setUser(UserUtil.setterUser(customer.getUser()));
        return customerRes;
    }

    @Override
    public Customer updateCustomer(CustomerUpdateDto customerUpdateDto) {

        if(!customerRepository.existsById(customerUpdateDto.getId())) {
            throw new EntityNotFoundException("Customer not found!");
        }
        Customer customer = customerRepository.findById(customerUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        Customer updateCustomer = new Customer();
        updateCustomer.setId(customerUpdateDto.getId());

        User user = new User();
        user.setId(customerUpdateDto.getUser().getId());
        user.setFirstName(customerUpdateDto.getUser().getFirstName());
        user.setLastName(customerUpdateDto.getUser().getLastName());
        user.setEmail(customerUpdateDto.getUser().getEmail());
        user.setPhoneNumber(customerUpdateDto.getUser().getPhoneNumber());
        user.setAddress(customerUpdateDto.getUser().getAddress());
        user.setCreatedAt(customer.getUser().getCreatedAt());
        user.setRole(Role.CUSTOMER);
        if(customerUpdateDto.getUser().getPassword() != null &&  !passwordEncoder.matches(customerUpdateDto.getUser().getPassword(), customer.getUser().getPassword())){
            user.setPassword(passwordEncoder.encode(customerUpdateDto.getUser().getPassword()));
        }else{
            user.setPassword(customer.getUser().getPassword());
        }

        updateCustomer.setUser(user);
        return customerRepository.save(updateCustomer);
    }

    @Override
    public void deleteCustomerById(int id) {
        if(!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found!");
        }
        customerRepository.deleteById(id);
    }
}
