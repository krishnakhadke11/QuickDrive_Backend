package com.TransportationService.validation;

import com.TransportationService.dto.request.DriverDto;
import com.TransportationService.dto.request.UserDetailsDto;
import com.TransportationService.dto.request.UserUpdateDetailsDto;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidation {

    public void validateUser(UserDetailsDto userDetailsDto) {
        String firstName = userDetailsDto.getFirstName();
        String lastName = userDetailsDto.getLastName();
        String password = userDetailsDto.getPassword();
        String phoneNumber = userDetailsDto.getPhoneNumber();
        validateDetails(firstName,lastName,password,phoneNumber);
        String email = userDetailsDto.getEmail();
        validateEmail(email);
    }

    public void validateUser(UserUpdateDetailsDto userUpdateDetailsDto) {
        String firstName = userUpdateDetailsDto.getFirstName();
        String lastName = userUpdateDetailsDto.getLastName();
        String password = userUpdateDetailsDto.getPassword();
        String phoneNumber = userUpdateDetailsDto.getPhoneNumber();
        validateDetails(firstName,lastName,password,phoneNumber);
        String email = userUpdateDetailsDto.getEmail();
        validateEmail(email);
    }

    private void validateDetails(String firstName, String lastName, String password, String phoneNumber) {
        if(firstName == null || firstName.length() <3 ||firstName.length() >50){
            throw new IllegalArgumentException("firstname should be between 3 and 50 characters");
        }

        if(lastName == null || lastName.length() <3 ||lastName.length() >50){
            throw new IllegalArgumentException("lastName should be between 3 and 50 characters");
        }
        if(password != null){
            validatePassword(password);
        }

        if(phoneNumber == null || phoneNumber.length() != 10){
            throw new IllegalArgumentException("Phone Number is not valid");
        }
    }

    private void validateEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    private void validatePassword(String password){
        // ?= checks the presence of pattern in a string
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()!])(?=\\S+$).{8,20}$");
        if(!pattern.matcher(password).matches()){
            throw new IllegalArgumentException("Password must contain atleast one lower and uppercase character, " +
                    "one digit, one special character and 8 characters");
        }
    }
}
