package com.lewyonq.flashqi.exception;

public class CardNotFoundException extends FlashqiException {
    public CardNotFoundException(Long id) {
        super("Card not found with id: " + id);
    }
} 