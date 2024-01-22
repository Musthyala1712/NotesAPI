package org.example.controller;

import jakarta.validation.Valid;
import org.example.exception.UserAlreadyExistsException;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.request.LoginRequest;
import org.example.request.UserRequest;
import org.example.response.ResponseBase;
import org.example.response.TokenResponse;
import org.example.response.UserResponseBase;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseBase<TokenResponse>> signup(@RequestBody @Valid UserRequest userRequest) throws UserAlreadyExistsException {
        TokenResponse tokenResponse = userService.signUp(userRequest);
        ResponseBase<TokenResponse> responseBase = new ResponseBase<>(true, null, tokenResponse);
        return new ResponseEntity<>(responseBase, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBase<TokenResponse>> login(@RequestBody @Valid LoginRequest loginRequest) throws UserNotFoundException {
        TokenResponse tokenResponse = userService.login(loginRequest);
        ResponseBase<TokenResponse> responseBase = new ResponseBase<>(true, null, tokenResponse);
        return new ResponseEntity<>(responseBase, HttpStatus.OK);
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<UserResponseBase<User>> findUser(@PathVariable("mobileNumber") String mobileNumber,  UserRequest userRequest) throws UserNotFoundException {
        User foundUser = userService.findUser(userRequest);
        UserResponseBase<User> responseBase = new UserResponseBase<>(true, null, foundUser);
        return new ResponseEntity<>(responseBase, HttpStatus.OK);
    }
}
