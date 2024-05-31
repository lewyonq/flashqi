package com.lewyonq.flashqi.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lewyonq.flashqi.deck.DeckService;
import com.lewyonq.flashqi.deck.Deck;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final DeckService deckService;

    public void saveCard(CardRequest cardRequest) {
        Deck deck = deckService.getDeckById(cardRequest.getDeckId());
        Card card = cardMapper.mapRequestToEntity(cardRequest, deck);
        this.cardRepository.save(card);
    }

    public List<Card> getAllCards() {
        return this.cardRepository.findAll();
    }

}
