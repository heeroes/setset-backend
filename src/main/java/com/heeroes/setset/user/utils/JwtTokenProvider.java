package com.heeroes.setset.user.utils;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {
    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretkey){
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generate(int userId,String subject, Date expiredAt){
    	 return Jwts.builder()
                 .claim("userId", userId)

                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    	
    }


    public int extractUserId(String accessToken){
        Claims claims = parseClaims(accessToken);
        System.out.println("claims : " + claims);
        System.out.println("userID:" + claims.get("userId"));
        return (int) claims.get("userId");

    }

    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
    
    // 토큰 유효성 검사
    public boolean isValid(String token) {
        try {
            Jwts.parser().verifyWith(this.key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
        	log.debug("토큰 유효성 검증 오류 : {}",e.getMessage());
            return false;
        }
    }
}
