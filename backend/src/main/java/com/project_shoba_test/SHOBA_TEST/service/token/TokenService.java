package com.project_shoba_test.SHOBA_TEST.service.token;

import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {

    public String generateAccessToken(String username, boolean rememberMe);

    public String extractUsername(String token);

    public boolean validateToken(String token);

    public void addTokenToCookie(String token, HttpServletResponse response, boolean rememberMe);

    public void deleteCookieFromResponse(HttpServletResponse response);
}
