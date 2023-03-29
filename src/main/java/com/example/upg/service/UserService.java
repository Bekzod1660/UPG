package com.example.upg.service;


import com.example.upg.dto.ApiResponse;
import com.example.upg.dto.ResponseMessage;
import com.example.upg.dto.UserDto;
import com.example.upg.dto.UserLoginDto;
import com.example.upg.entity.UserEntity;
import com.example.upg.repositoriy.UserRepository;
import com.example.upg.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserDto,ApiResponse>{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public ApiResponse delete(int id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.delete(byId.get());
            return new ApiResponse(
                    ResponseMessage.SUCCESS.getStatusCode(),
                    ResponseMessage.SUCCESS.getMessage()
            );
        }
        return new ApiResponse(
                ResponseMessage.ERROR_USER_NOT_FOUND.getStatusCode(),
                ResponseMessage.ERROR_USER_NOT_FOUND.getMessage()
        );
    }

    @Override
    public ApiResponse list() {
        List<UserEntity> all = userRepository.findAll();
        if (all.size() == 0) {
            return new ApiResponse(
                    ResponseMessage.ERROR_USER_NOT_FOUND.getStatusCode(),
                    ResponseMessage.ERROR_USER_NOT_FOUND.getMessage()
            );
        }
        return new ApiResponse(
                ResponseMessage.SUCCESS.getStatusCode(),
                ResponseMessage.SUCCESS.getMessage(),
                all
        );
    }

    @Override
    public ApiResponse update(int id, UserDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            return new ApiResponse(
                    ResponseMessage.ERROR_USER_NOT_FOUND.getStatusCode(),
                    ResponseMessage.ERROR_USER_NOT_FOUND.getMessage()
            );
        }
        UserEntity user = optionalUser.get();
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setActive(true);
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUsername(userDto.getUsername());

        return new ApiResponse(
                ResponseMessage.SUCCESS.getStatusCode(),
                ResponseMessage.SUCCESS.getMessage(),
                userRepository.save(user)
        );
    }

    @Override
    public ApiResponse add(UserDto userDto) {
        Optional<UserEntity> currentUser = userRepository.findByPhoneNumberAndEmail(userDto.getPhoneNumber(), userDto.getEmail());
        if (currentUser.isPresent()) {
            return new ApiResponse(
                    ResponseMessage.ERROR_USER_ALREADY_EXIST.getStatusCode(),
                    ResponseMessage.ERROR_USER_NOT_FOUND.getMessage(),
                    currentUser.get()
            );
        }
        UserEntity user = UserEntity.ofUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return new ApiResponse(
                ResponseMessage.SUCCESS.getStatusCode(),
                ResponseMessage.SUCCESS.getMessage(),
                userRepository.save(user)
        );
    }
    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("user %s not found", username)));
    }


    public ApiResponse getById(int id) {
        return new ApiResponse(
                ResponseMessage.SUCCESS.getStatusCode(),
                ResponseMessage.SUCCESS.getMessage(),
                userRepository.findById(id)
        );
    }
    public ApiResponse login(UserLoginDto userDto) throws UsernameNotFoundException, JsonProcessingException {
        UserEntity user= getByUsername(userDto.getUsername());
        if (user == null ) {
            throw new UsernameNotFoundException("username or password is incorrect");
        }
        if (!passwordEncoder.matches(userDto.getPassword(),  user.getPassword())){
            throw new UsernameNotFoundException("username or password is incorrect");

        }
        return new ApiResponse(
                ResponseMessage.SUCCESS.getStatusCode(),
                ResponseMessage.SUCCESS.getMessage(),
                Map.of("access_token",jwtUtils.generateAccessToken(user),
                        "refresh_token",jwtUtils.generateRefreshToken(user))
        );
    }

    public ApiResponse getAccessToken(String refreshToken) {
        Claims accessClaims = jwtUtils.isRefreshValid(refreshToken);
        if (accessClaims!=null){
            String subject = accessClaims.getSubject();
            UserEntity repository = userRepository.findByUsername(subject).get();
            if (repository!=null){
                return new ApiResponse(
                        ResponseMessage.SUCCESS.getStatusCode(),
                        ResponseMessage.SUCCESS.getMessage(),
                        Map.of("access_token",jwtUtils.generateAccessToken(repository))
                );

            }
        }
        return null;
    }





}
