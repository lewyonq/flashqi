package com.lewyonq.flashqi.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lewyonq.flashqi.card.Card;

public class DeckTest {
    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void shouldSetAndGetName() {
        String name = "Test name";
        deck.setName(name);
        assertEquals(name, deck.getName());
    }

    @Test
    void shoulSetAndGetDescription() {
        String description = "Test name";
        deck.setDescription(description);
        assertEquals(description, deck.getDescription());
    }

    @Test
    void shouldGetCards() {
        List<Card> cards = deck.getCards();
        assertNull(cards);
    }

    @Test
    void shouldNotAllowNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            deck.setName(null);
        });
    }

    @Test
    void shouldAllowNullDescription() {
        deck.setDescription(null);
        assertEquals(null, deck.getDescription());
    }
}
