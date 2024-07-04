package com.TransportationService.service;


import com.TransportationService.entity.User;

import java.util.List;

public interface UserService{
    public List<User> getAllUsers();

    public User findUserById(int id);

    public User addUser(User user);
}
