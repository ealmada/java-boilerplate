package com.asapp.backend.challenge.service;

import com.asapp.backend.challenge.repository.UserRepository;
import com.asapp.backend.challenge.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public UserResource save(UserResource user){
        return userRepository.save(user);
    }

    public Optional<UserResource> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserResource> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
