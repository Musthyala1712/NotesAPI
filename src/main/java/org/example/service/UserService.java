package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.UserAlreadyExistsException;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.request.LoginRequest;
import org.example.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User signUp(UserRequest request) throws UserAlreadyExistsException {
        log.info("creating a new user");
        Optional<User> foundUser = userRepository.findByEmail(request.getEmail());
        if (foundUser.isEmpty()) {
            User user = User.builder().email(request.getEmail()).password(request.getPassword()).firstName(request.getFirstName()).lastName(request.getLastName()).mobileNumber(request.getMobileNumber()).build();
            return userRepository.save(user);
        }
        throw new UserAlreadyExistsException("the user already exists");
    }

    public boolean login(LoginRequest loginRequest) throws UserNotFoundException {
        log.info("logging in");
        Optional<User> foundUser = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        return true;
    }

    public User findUser(UserRequest userRequest) throws UserNotFoundException {
        log.info("user details");
        Optional<User> foundUser = userRepository.findByMobileNumber(userRequest.getMobileNumber());
        if (foundUser.isPresent()) {
            return foundUser.get();
        }
        throw new UserNotFoundException("user not found");
    }

}
