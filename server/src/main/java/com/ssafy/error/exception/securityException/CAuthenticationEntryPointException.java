package com.ssafy.error.exception.securityException;

public class CAuthenticationEntryPointException extends RuntimeException{
    public CAuthenticationEntryPointException(){
        super();
    }

    public CAuthenticationEntryPointException(String message){

        super(message);
    }
    public CAuthenticationEntryPointException(String message, Throwable e){

        super(message,e);
    }
}
