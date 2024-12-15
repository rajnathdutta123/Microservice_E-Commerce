package com.raj.api_gateway.service;

import com.raj.api_gateway.dto.UserDTO;
import com.raj.api_gateway.model.UserInfo;
import com.raj.api_gateway.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfo userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    public UserDTO getUserByUsername(String username)
    {
        UserInfo userDetail = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        List<String> roles = Arrays.stream(userDetail.getRoles().split(",")).toList();
        return UserDTO.builder()
                .id(userDetail.getId())
                .name(userDetail.getName())
                .email(userDetail.getEmail())
                .roles(roles)
                .isActive(userDetail.isActive())
                .build();
    }
    public UserDTO getUserFromToken(String token) {
        Jwt jwt = jwtDecoder.decode(token.replace("Bearer ", ""));
        String username = jwt.getSubject();
        String userId = jwt.getClaim("id");
        List<String> roles = jwt.getClaim("roles");
        String name=jwt.getClaim("name");
        boolean isActive=jwt.getClaim("isActive");
        return UserDTO.builder()
                .id(Integer.parseInt(userId))
                .name(name)
                .email(username)
                .roles(roles)
                .isActive(isActive)
                .build();
    }

}
