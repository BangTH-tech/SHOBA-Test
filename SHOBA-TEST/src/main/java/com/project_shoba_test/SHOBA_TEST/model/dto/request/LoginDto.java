package com.project_shoba_test.SHOBA_TEST.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required") 
    private String password;

    private String recaptchaToken;
}
