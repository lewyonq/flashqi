package com.lewyonq.flashqi.deck;

import com.lewyonq.flashqi.card.Card;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {
    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Deck saveDeck(DeckDTO deckDTO) {
        Deck deck = mapToDeck(deckDTO);
        return deckRepository.save(deck);
    }

    public List<Deck> getDecks() {
        return deckRepository.findAll();
    }

    public Card addCardToDeck(Long deckId, Card card) {
        //todo: add custom exception
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        deck.getCards().add(card);
        deckRepository.save(deck);

        return card;
    }

    private Deck mapToDeck(DeckDTO deckDTO) {
        Deck deck = new Deck();
        deck.setName(deckDTO.getName());
        deck.setDescription(deckDTO.getDescription());
        return deck;
    }
}
