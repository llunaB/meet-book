package com.ssafy.error;

import com.ssafy.error.exception.AlreadyExistEmailException;
import com.ssafy.error.exception.AlreadyExistNicknameException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (AlreadyExistNicknameException.class)
    protected ResponseEntity<Object> handleAlreadyExistNickname(AlreadyExistNicknameException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("statusValue", HttpStatus.BAD_REQUEST.value());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (AlreadyExistEmailException.class)
    protected ResponseEntity<Object> handleAlreadyExistEmail(AlreadyExistEmailException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("statusValue", HttpStatus.BAD_REQUEST.value());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("statusValue", status.value());
        body.put("status", status);

        // Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }
}
