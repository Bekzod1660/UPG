package com.example.upg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String name;
    private String password;
    private String username;
    private int phoneNumber;
    private String email;




}
