package com.raj.api_gateway.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<String> roles;
    private boolean isActive;

}
