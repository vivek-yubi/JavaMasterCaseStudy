package com.busservice.routeapp.service;

import com.busservice.routeapp.dto.UserResponseDto;
import com.busservice.routeapp.model.UserAccount;

import java.util.List;

public interface UserService {


    public UserResponseDto signUp(UserAccount user);

    public UserResponseDto login(UserAccount user);

    public UserResponseDto updateRole(UserAccount user);

    public List<UserResponseDto> getAllUsers();

    public UserAccount validateToken (String token);
}