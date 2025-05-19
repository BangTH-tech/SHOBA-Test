package com.project_shoba_test.SHOBA_TEST.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.exception.UnauthorizedException;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.jwt.access-token.expiry-time}")
    private int accessTokenExpiryTime;


    @Override
    public String generateAccessToken(String username) {

        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiryTime))
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUsername(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new UnauthorizedException("Token invalid", "Token invalid");
        }
    }

    @Override
    public boolean validateToken(String token) {
        final String userName = extractUsername(token);
        return (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    @Override
    public void addTokenToCookie(String token, HttpServletResponse response) {
        int expiryTime = accessTokenExpiryTime;
        
        ResponseCookie tokenCookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(expiryTime / 1000)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());

    }

    @Override
    public void deleteCookieFromResponse(HttpServletResponse response) {
        ResponseCookie tokenCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true) // Cần thiết khi dùng SameSite=None
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());

    }
}
