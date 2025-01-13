package com.lewyonq.flashqi.deck;

import com.lewyonq.flashqi.card.Card;
import com.lewyonq.flashqi.card.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;

    public DeckService(DeckRepository deckRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
    }

    public Deck saveDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    public List<Deck> getDecks() {
        return deckRepository.findAll();
    }

    public Card addCardToDeck(Long deckId, Card card) {
        //todo: add custom exception
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        if (card.getId() == null || cardRepository.findById(card.getId()).isEmpty()) {
            card = cardRepository.save(card);
        }
        deck.getCards().add(card);
        deckRepository.save(deck);

        return card;
    }
}
