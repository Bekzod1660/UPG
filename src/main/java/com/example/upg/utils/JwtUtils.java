package com.example.upg.utils;


import com.example.upg.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtUtils {


    public  String generateAccessToken(
            UserEntity userDetails
    )  {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "access_token")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() +(1000*60*2)))
                .compact();
    }

    public  String generateRefreshToken(
            UserEntity userDetails
    )  {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "refresh_token")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() +(1000*60*60*2)))
                .compact();
    }

    public  Claims isAccessValid(String token) {return getAccessClaims(token);}

    public  Claims isRefreshValid(String token) {return getRefreshClaims(token);}


    public  synchronized Claims getAccessClaims(String token) {
        try {
            return Jwts.parser().setSigningKey("access_token").parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public  synchronized Claims getRefreshClaims(String token) {
        try {
            return Jwts.parser().setSigningKey("refresh_token").parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
