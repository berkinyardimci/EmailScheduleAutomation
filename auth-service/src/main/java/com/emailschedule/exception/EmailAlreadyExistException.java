package com.emailschedule.exception;

public class EmailAlreadyExistException extends RuntimeException{
    private final String message;

    public EmailAlreadyExistException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
