package com.example.upg.controller;


import com.example.upg.dto.UserDto;
import com.example.upg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/add")
    public ResponseEntity<?> add( @RequestBody UserDto userRequestDto){

        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.add(userRequestDto));
    }

    @GetMapping("/list")
    public ResponseEntity<?>listObject(){
        return ResponseEntity.ok(userService.list());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteO(@PathVariable int id){
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable int id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>update(@RequestBody UserDto userRequestDto,@PathVariable int id){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.update(id,userRequestDto));
    }


}
