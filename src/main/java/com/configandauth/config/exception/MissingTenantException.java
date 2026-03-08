package com.configandauth.config.exception;

public class MissingTenantException extends RuntimeException {

    public MissingTenantException(String message) {
        super(message);
    }
}