package com.storemanagement.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationDto {
    private String username;
    private String password;
    private Role role;
}