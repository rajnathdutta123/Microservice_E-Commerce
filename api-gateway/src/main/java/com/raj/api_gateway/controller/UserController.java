package com.raj.api_gateway.controller;

import com.raj.api_gateway.dto.UserDTO;
import com.raj.api_gateway.model.AuthRequest;
import com.raj.api_gateway.model.User;
import com.raj.api_gateway.model.UserInfo;
import com.raj.api_gateway.service.JwtService;
import com.raj.api_gateway.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class UserController {

    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/welcome")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public String welcome() {
        return "Welcome this endpoint is not  secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile(@RequestHeader("Authorization") String token) {
        UserDTO user = userInfoService.getUserFromToken(token);
        if (user != null) {
            return "Welcome to User Profile " + user.getId();
        } else {
            return "User not authenticated";
        }
    }

    @GetMapping("/admin/profile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile(@AuthenticationPrincipal User user) {
        if (user != null) {
            return "Welcome to Admin Profile " + user.getId();
        } else {
            return "User not authenticated";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                List<String> roles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                UserDTO userDTO=userInfoService.getUserByUsername(authRequest.getUsername());
                return ResponseEntity.ok(jwtService.generateToken(authRequest.getUsername(), userDTO, roles));
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            // Log the error for better debugging
            log.error("Authentication failed: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}
