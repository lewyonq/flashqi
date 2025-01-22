package com.lewyonq.flashqi.exception;

public class DeckNotFoundException extends FlashqiException {
    public DeckNotFoundException(Long id) {
        super("Deck not found with id: " + id);
    }
} 