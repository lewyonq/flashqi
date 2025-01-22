package com.lewyonq.flashqi.exception;

public abstract class FlashqiException extends RuntimeException {
    protected FlashqiException(String message) {
        super(message);
    }
} 