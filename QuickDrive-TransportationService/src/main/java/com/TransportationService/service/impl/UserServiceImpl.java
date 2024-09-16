package com.TransportationService.service.impl;

import com.TransportationService.entity.User;
import com.TransportationService.repository.UserRepository;
import com.TransportationService.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User findUserById(int id){
        return userRepository.findById(id).get();
    }

    @Transactional
    public User addUser(User user){
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
