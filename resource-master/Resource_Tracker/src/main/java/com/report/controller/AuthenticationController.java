package com.report.controller;

import com.report.dto.LoginResponse;
import com.report.dto.LoginUserDto;
import com.report.dto.RegisterUserDto;
import com.report.entity.User;
import com.report.service.AuthenticationService;
import com.report.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    //
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    //
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setFullName(authenticatedUser.getFullName());
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestHeader("${security.jwt.header}") String oldToken) {
        if (oldToken != null && oldToken.startsWith("${security.jwt.prefix} ")) {
            String token = oldToken.substring("${security.jwt.prefix} ".length());

            // Extract username from token
            String username = jwtService.extractUsername(token);

            // Load UserDetails based on username
            UserDetails userDetails = authenticationService.loadUserByUsername(username);

            // Validate if the token is still valid for the user
            if (userDetails != null && jwtService.isTokenValid(token, userDetails)) {
                // Refresh token
                String refreshedToken = jwtService.refreshToken(token);

                // Create LoginResponse with refreshed token and expiration
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(refreshedToken);
                loginResponse.setExpiresIn(jwtService.getExpirationTime());

                return ResponseEntity.ok(loginResponse);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
