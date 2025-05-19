package com.project_shoba_test.SHOBA_TEST.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.project_shoba_test.SHOBA_TEST.model.exception.ErrorResponse;
import io.jsonwebtoken.MalformedJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity(new ErrorResponse(ex.getMessage(), ex.getDetails()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity(new ErrorResponse(ex.getMessage(), ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    // handle request param bi sai dinh dang
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity(new ErrorResponse(ex.getMessage(), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // handle jwt k dung dinh dang
    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMalformedJwtThrowException(MalformedJwtException ex) {
        return new ResponseEntity(new ErrorResponse(ex.getMessage(), ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }
}
