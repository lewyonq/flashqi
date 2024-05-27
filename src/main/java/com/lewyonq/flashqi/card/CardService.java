package com.lewyonq.flashqi.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public void saveCard(CardRequest cardRequest) {
        Card card = mapCardRequestToCard(cardRequest);
        this.cardRepository.save(card);
    }

    private Card mapCardRequestToCard(CardRequest cardRequest) {
        return Card
                .builder()
                .answer(cardRequest.getAnswer())
                .question(cardRequest.getQuestion())
                .decks(new ArrayList<>())
                .howManyFailed(0L)
                .howManyWatched(0L)
                .howManyPassed(0L)
                .build();
    }
}
