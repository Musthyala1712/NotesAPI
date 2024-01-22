package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.UserAlreadyExistsException;
import org.example.exception.UserNotFoundException;
import org.example.model.PrincipalUser;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.request.LoginRequest;
import org.example.request.UserRequest;
import org.example.response.JwtAccessToken;
import org.example.response.TokenResponse;
import org.example.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public TokenResponse signUp(UserRequest request) throws UserAlreadyExistsException {
        log.info("creating a new user");
        Optional<User> foundUser = userRepository.findByEmail(request.getEmail());
        if (foundUser.isEmpty()) {
            User user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).firstName(request.getFirstName()).lastName(request.getLastName()).mobileNumber(request.getMobileNumber()).build();
            User createdUser = userRepository.save(user);
            PrincipalUser principalUser = new PrincipalUser(createdUser);
            JwtAccessToken jwtAccessToken = jwtService.generateToken(principalUser);
            return new TokenResponse(createdUser, jwtAccessToken);
        }
        throw new UserAlreadyExistsException("the user already exists");
    }

    public TokenResponse login(LoginRequest loginRequest) throws UserNotFoundException {
        log.info("logging in...");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>()));
        Optional<User> foundUser = userRepository.findByEmail(loginRequest.getEmail());
        if (foundUser.isPresent()) {
            JwtAccessToken jwtAccessToken = jwtService.generateToken(new PrincipalUser(foundUser.get()));
            return new TokenResponse(foundUser.get(), jwtAccessToken);
        }
        throw new BadCredentialsException("Invalid username/password. Please try again!");
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
