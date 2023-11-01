package com.vention.trackloader.exceptions;

public class AccessRestrictedException extends RuntimeException{
    public AccessRestrictedException(String message) {
        super(message);
    }
}
