package com.busservice.routeapp.security;

import com.example.casestudy.model.UserAccount;
import com.example.casestudy.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        UserAccount user = userService.validateToken(token);
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (user.getIsAdmin()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new User(token, "", authorities);
        } else {
            throw new UsernameNotFoundException("User not found for the provided token");
        }
    }
}
