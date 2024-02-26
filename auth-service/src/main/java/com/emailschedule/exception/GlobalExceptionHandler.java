package com.emailschedule.exception;

import com.emailschedule.constant.ErrorMessages;
import com.emailschedule.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> emailExistException(final EmailAlreadyExistException exception){
        var customRestError = ErrorResponse.builder()
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return responseEntity(HttpStatus.BAD_REQUEST, customRestError);
    }
    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<ErrorResponse> passwordNotMatchesException(final PasswordNotMatchesException exception){
        var customRestError = ErrorResponse.builder()
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return responseEntity(HttpStatus.BAD_REQUEST, customRestError);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> passwordNotMatchesException(final InvalidPasswordException exception){
        var customRestError = ErrorResponse.builder()
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return responseEntity(HttpStatus.BAD_REQUEST, customRestError);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> passwordNotMatchesException(final UserNotFoundException exception){
        var customRestError = ErrorResponse.builder()
                .message(exception.getMessage())
                .isSuccess(false)
                .build();
        return responseEntity(HttpStatus.BAD_REQUEST, customRestError);
    }
    private static ResponseEntity<ErrorResponse> responseEntity(HttpStatus httpStatus, ErrorResponse errorResponse){
        return ResponseEntity.status(httpStatus.value())
                .body(errorResponse);
    }
}
