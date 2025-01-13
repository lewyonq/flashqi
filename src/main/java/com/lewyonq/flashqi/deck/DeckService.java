package com.lewyonq.flashqi.deck;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {
    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Deck saveDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    public List<Deck> getDecks() {
        return deckRepository.findAll();
    }
}
