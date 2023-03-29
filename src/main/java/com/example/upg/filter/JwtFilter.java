package com.example.upg.filter;


import com.example.upg.entity.UserEntity;
import com.example.upg.service.UserService;
import com.example.upg.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader ==null || !requestHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = requestHeader.replace("Bearer ", "");
        Claims claims= jwtUtils.isAccessValid(token);
        if (claims==null){
            filterChain.doFilter(request,response);
            return;
        }
        String username = claims.getSubject();
        UserEntity userFromRedis = userService.getByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        userFromRedis.getAuthorities()

                );
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }


}
