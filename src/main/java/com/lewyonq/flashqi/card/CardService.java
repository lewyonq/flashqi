package com.lewyonq.flashqi.card;

import com.lewyonq.flashqi.deck.Deck;
import com.lewyonq.flashqi.deck.DeckRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    public CardService(CardRepository cardRepository, DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    public Card saveCard(CardDTO cardDTO) {
        Card card = mapToCard(cardDTO);
        return cardRepository.save(card);
    }

    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    //todo: add own exception

    public Card addCardToDeck(Long cardId, Long deckId) {
        Card card = cardRepository.findById(cardId).orElseThrow();
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        deck.getCards().add(card);
        deckRepository.save(deck);

        return card;
    }


    private Card mapToCard(CardDTO cardDTO) {
        Card card = new Card();
        card.setQuestion(cardDTO.getQuestion());
        card.setAnswer(cardDTO.getAnswer());
        return card;
    }
}
