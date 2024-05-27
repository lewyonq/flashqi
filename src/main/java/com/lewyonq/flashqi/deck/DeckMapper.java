package com.lewyonq.flashqi.deck;

import org.springframework.stereotype.Component;

@Component
public class DeckMapper {
    public Deck mapRequestToEntity(DeckRequest deckRequest) {
        return Deck.builder()
        .name(deckRequest.getName())
        .description(deckRequest.getDescription())
        .deckCategories(deckRequest.getDeckCategories())
        .customCategories(deckRequest.getCustomCategories())
        .build();
    }

    public void updateEntityFromRequest(Deck deck, DeckRequest deckRequest) {
        deck.setName(deckRequest.getName());
        deck.setDescription(deckRequest.getDescription());
        deck.setDeckCategories(deckRequest.getDeckCategories());
        deck.setCustomCategories(deckRequest.getCustomCategories());
    }
}

