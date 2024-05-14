package com.heeroes.setset.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
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
		ClaimsBuilder claims = Jwts.claims()
				.setSubject(subject) // 토큰 제목 설정 ex) access-token, refresh-token
				.setIssuedAt(new Date()) // 생성일 설정
//				만료일 설정 (유효기간)
				.setExpiration(expiredAt);

		claims.add("userId", userId);
    	
    	 return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    	
    }

    public String extractEmail(String accessToken){
        Claims claims = parseClaims(accessToken);
        return (String) claims.get("email");
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
