package com.project_shoba_test.SHOBA_TEST.model.dto.response.log;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HttpLogResponse {
    private String method;
    private String url;
    private String requestBody;
    private String responseBody;
    private int responseStatus;
    private LocalDateTime timestamp;
}
