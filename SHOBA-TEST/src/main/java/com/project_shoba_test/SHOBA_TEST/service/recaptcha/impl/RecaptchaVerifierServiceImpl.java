package com.project_shoba_test.SHOBA_TEST.service.recaptcha.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.recaptcha.RecaptchaResponse;
import com.project_shoba_test.SHOBA_TEST.service.recaptcha.RecaptchaVerifierService;

@Service
public class RecaptchaVerifierServiceImpl implements RecaptchaVerifierService {

    @Value("${google.recaptcha.secret-key}")
    private String recaptchaSecret;

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Override
    public boolean verifyToken(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("secret", recaptchaSecret);
        request.add("response", token);

        HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<>(request, headers);

        ResponseEntity<RecaptchaResponse> response = restTemplate.postForEntity(
                VERIFY_URL, httpRequest, RecaptchaResponse.class);

        return response.getBody() != null && response.getBody().isSuccess();
    }

}
