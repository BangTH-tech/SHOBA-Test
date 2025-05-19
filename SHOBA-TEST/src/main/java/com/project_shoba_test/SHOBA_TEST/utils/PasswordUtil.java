package com.project_shoba_test.SHOBA_TEST.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public static String encodePassword(String password) {
        return encoder.encode(password);
    }

    public static boolean verifyPassword(String password, String encryptedPassword) {
        return encoder.matches(password, encryptedPassword);
    }
}
