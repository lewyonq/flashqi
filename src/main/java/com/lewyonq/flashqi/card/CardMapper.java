package com.lewyonq.flashqi.card;

import org.springframework.stereotype.Component;

import com.lewyonq.flashqi.deck.Deck;

@Component
public class CardMapper {
    

    public Card mapRequestToEntity(CardRequest cardRequest, Deck deck) {
        return Card
                .builder()
                .answer(cardRequest.getAnswer())
                .question(cardRequest.getQuestion())
                .deck(deck)
                .howManyFailed(0L)
                .howManyWatched(0L)
                .howManyPassed(0L)
                .build();
    }
}
