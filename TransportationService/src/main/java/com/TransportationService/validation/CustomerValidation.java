package com.TransportationService.validation;

import com.TransportationService.dto.request.CustomerDto;
import com.TransportationService.dto.request.CustomerUpdateDto;
import com.TransportationService.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidation {
    private final UserValidation userValidation;

    public CustomerValidation(UserValidation userValidation) {
        this.userValidation = userValidation;
    }

    public void validateCustomer(CustomerDto customerDto) {
        userValidation.validateUser(customerDto.getUser());
    }
    public void validateCustomer(CustomerUpdateDto customerUpdateDto) {
        userValidation.validateUser(customerUpdateDto.getUser());
    }
}
