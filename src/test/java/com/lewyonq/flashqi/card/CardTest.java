package com.lewyonq.flashqi.card;

import com.lewyonq.flashqi.deck.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    
    private Card card;
    
    @BeforeEach
    void setUp() {
        card = new Card();
    }

    @Test
    void shouldSetAndGetQuestion() {
        String question = "Test Question";
        card.setQuestion(question);
        assertEquals(question, card.getQuestion());
    }

    @Test
    void shouldSetAndGetAnswer() {
        String answer = "Test Answer";
        card.setAnswer(answer);
        assertEquals(answer, card.getAnswer());
    }

    @Test
    void shouldGetId() {
        Long id = card.getId();
        assertNull(id);
    }

    @Test
    void shouldGetDecks() {
        List<Deck> decks = card.getDecks();
        assertNull(decks);
    }

    @Test
    void shouldNotAllowNullQuestion() {
        assertThrows(IllegalArgumentException.class, () -> {
            card.setQuestion(null);
        });
    }

    @Test
    void shouldNotAllowNullAnswer() {
        assertThrows(IllegalArgumentException.class, () -> {
            card.setAnswer(null);
        });
    }
} 