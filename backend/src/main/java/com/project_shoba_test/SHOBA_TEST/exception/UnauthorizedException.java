package com.project_shoba_test.SHOBA_TEST.exception;

public class UnauthorizedException extends RuntimeException {
    private String message;

    private String details;

    public UnauthorizedException(String message, String details) {
        super(message);
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}