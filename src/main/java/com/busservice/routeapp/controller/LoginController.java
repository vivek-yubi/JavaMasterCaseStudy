package com.busservice.routeapp.controller;

import com.example.casestudy.dto.UserResponseDto;
import com.example.casestudy.model.UserAccount;
import com.example.casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/allUsers")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserResponseDto> signUpUser(@Valid @RequestBody UserAccount user) {
        try
        {
            UserResponseDto signedUpUser= userService.signUp(user);
            return ResponseEntity.status(HttpStatus.OK).body(signedUpUser);

        }
        catch (Exception e)
        {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserResponseDto.builder().message(e.getMessage()).build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login (@Valid @RequestBody UserAccount user) {
        try
        {
            UserResponseDto loggedInUser= userService.login(user);
            return ResponseEntity.status(HttpStatus.OK).body(loggedInUser);

        }
        catch (Exception e)
        {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserResponseDto.builder().message(e.getMessage()).build());
        }

    }
}
