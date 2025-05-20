package com.project_shoba_test.SHOBA_TEST.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "log")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HttpLog {
    @Id
    private String id;
    private String method;
    private String url;
    private String requestBody;
    private String responseBody;
    private int responseStatus;
    private Date createdAt;
    private String createdBy;
}
