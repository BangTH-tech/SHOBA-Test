package com.project_shoba_test.SHOBA_TEST.model.dto.response.recaptcha;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecaptchaResponse {
    private boolean success;
    private List<String> errorCodes;
}
