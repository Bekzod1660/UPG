package com.example.upg.dto;

import lombok.Data;

@Data
public class AdminRequestDto {

    private String name;

    private String username;

    private String password;

    private int phoneNumber;

    private String email;

    private String role;

    private String permission;
}
