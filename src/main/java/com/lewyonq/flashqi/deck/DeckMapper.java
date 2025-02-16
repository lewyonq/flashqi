package com.lewyonq.flashqi.deck;

import org.springframework.stereotype.Component;

import com.lewyonq.flashqi.card.CardMapper;

@Component
public class DeckMapper {
    private final CardMapper cardMapper;

    public DeckMapper(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public Deck mapToDeck(DeckDTO deckDTO) {
        Deck deck = new Deck();
        deck.setTitle(deckDTO.getTitle());
        deck.setDescription(deckDTO.getDescription());
        return deck;
    }

    public DeckDTO mapToDTO(Deck deck) {
        DeckDTO deckDTO = new DeckDTO();
        deckDTO.setId(deck.getId());
        deckDTO.setTitle(deck.getTitle());
        deckDTO.setDescription(deck.getDescription());
        if (deck.getCards() != null) {
            deckDTO.setCards(deck.getCards()
            .stream()
            .map(cardMapper::mapToDTO)
            .toList());    
        }
        return deckDTO;
    }
}
