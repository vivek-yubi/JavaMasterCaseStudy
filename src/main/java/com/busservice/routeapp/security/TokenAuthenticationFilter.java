package com.busservice.routeapp.security;

import com.google.gson.JsonObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null) {
            try {
                Authentication authentication = new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
                Authentication authenticated = authenticationManager.authenticate(authentication);

                SecurityContextHolder.getContext().setAuthentication(authenticated);
            } catch (UsernameNotFoundException error) {
                sendErrorResponse(request, response, HttpServletResponse.SC_UNAUTHORIZED, error.getMessage());
                return;
            }
            catch (AuthenticationException e)
            {
                sendErrorResponse(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                return;
            }
        }
        else
        {
            sendErrorResponse(request, response, HttpServletResponse.SC_UNAUTHORIZED, "Token Not Found");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, int httpStatus, String errorMessage) throws IOException {
        response.setStatus(httpStatus);

        // Create a JSON object to represent the error response
        JsonObject errorResponse = new JsonObject();
        errorResponse.addProperty("timestamp", LocalDateTime.now().toString()); // Use your preferred method to get the timestamp
        errorResponse.addProperty("status", httpStatus);
        errorResponse.addProperty("error", httpStatus == HttpServletResponse.SC_UNAUTHORIZED ? "Unauthorized" : "Forbidden");
        errorResponse.addProperty("message", errorMessage);
        errorResponse.addProperty("path", request.getRequestURI());

        // Write the JSON response to the output
        response.getWriter().write(errorResponse.toString());
        response.getWriter().flush();
    }

    private String extractToken(HttpServletRequest request) {
        return request.getHeader("auth_token");
    }
}
