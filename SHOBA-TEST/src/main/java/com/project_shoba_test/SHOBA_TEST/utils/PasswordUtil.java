package com.project_shoba_test.SHOBA_TEST.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;
    private static final SecureRandom random = new SecureRandom();

    public static String encodePassword(String password) {
        return encoder.encode(password);
    }

    public static boolean verifyPassword(String password, String encryptedPassword) {
        return encoder.matches(password, encryptedPassword);
    }

    public static String generateRandomPassword() {
        List<Character> passwordChars = new ArrayList<>();
        
        for (int i = 0; i < 20; i++) {
            passwordChars.add(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        Collections.shuffle(passwordChars);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
