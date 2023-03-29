package com.example.upg.controller;

import com.example.upg.dto.UserDto;
import com.example.upg.dto.UserLoginDto;
import com.example.upg.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("register")
    public String register(
            @RequestBody UserDto userRegisterDto){
          ResponseEntity.status(HttpStatus.CREATED).body(userService.add(userRegisterDto));
        return "hom";

    }
    @PostMapping("login")
    public String login(
            @RequestBody UserLoginDto usernamePasswordRequestDto) throws JsonProcessingException {
         ResponseEntity.ok(userService.login(usernamePasswordRequestDto));
         return "hom";
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(
            @RequestBody Map<String,String>stringMap
            ) {
        return ResponseEntity.ok(userService.getAccessToken(stringMap.get("refresh_token")));
    }


}
