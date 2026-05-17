package com.utilityinternational.utility_backend.exception;

public class TokenRefreshException extends RuntimeException {
 
    public TokenRefreshException(String token, String message) {
        super(String.format("Token refresh failed [%s]: %s", token, message));
    }
}
 