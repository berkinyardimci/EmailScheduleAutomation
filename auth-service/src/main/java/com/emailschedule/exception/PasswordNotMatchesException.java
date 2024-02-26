package com.emailschedule.exception;

public class PasswordNotMatchesException extends RuntimeException{
    private final String message;

    public PasswordNotMatchesException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
