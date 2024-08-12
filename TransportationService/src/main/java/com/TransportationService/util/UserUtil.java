package com.TransportationService.util;

import com.TransportationService.dto.response.UserResponseDto;
import com.TransportationService.entity.User;

public class UserUtil {

    public static UserResponseDto setterUser(User user){
        UserResponseDto userRes = new UserResponseDto();
        userRes.setId(user.getId());
        userRes.setFirstName(user.getFirstName());
        userRes.setLastName(user.getLastName());
        userRes.setEmail(user.getEmail());
        userRes.setPhoneNumber(user.getPhoneNumber());
        userRes.setAddress(user.getAddress());
        userRes.setRole(user.getRole());

       return userRes;
    }
}
