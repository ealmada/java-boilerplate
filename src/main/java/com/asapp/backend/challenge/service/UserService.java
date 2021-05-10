package com.asapp.backend.challenge.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;
import com.asapp.backend.challenge.exceptions.UserAlreadyExistsException;
import com.asapp.backend.challenge.repository.UserRepository;
import com.asapp.backend.challenge.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResource save(UserResource user) throws UserAlreadyExistsException {
        Optional<UserResource> userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB.isPresent()) {
            throw new UserAlreadyExistsException(userFromDB.get());
        }

        byte[] encryptedPassword = BCrypt.with(LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2A)).hash(6, user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(encryptedPassword.toString());
        user.setEncryptedPassword(encryptedPassword);

        return userRepository.save(user);
    }

    public Optional<UserResource> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserResource> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
