package com.lewyonq.flashqi.card;

import com.lewyonq.flashqi.deck.Deck;
import com.lewyonq.flashqi.deck.DeckRepository;
import com.lewyonq.flashqi.exception.DeckNotFoundException;
import com.lewyonq.flashqi.exception.CardNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, DeckRepository deckRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
        this.cardMapper = cardMapper;
    }

    public CardDTO saveCard(CardDTO cardDTO) {
        Card card = cardMapper.mapToCard(cardDTO);
        return cardMapper.mapToDTO(cardRepository.save(card));
    }

    public List<CardDTO> getCards() {
        return cardRepository.findAll()
                .stream()
                .map(cardMapper::mapToDTO)
                .toList();
    }

    public CardDTO addCardToDeck(Long cardId, Long deckId) {
        if (cardId == null || deckId == null) {
            throw new IllegalArgumentException("Card ID and Deck ID cannot be null");
        }
        
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException(deckId));

        if (deck.getCards().stream().anyMatch(c -> c.getId().equals(cardId))) {
            throw new IllegalStateException("This card is already added to deck " + deck.getName() + "!");
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        deck.getCards().add(card);
        deckRepository.save(deck);

        return cardMapper.mapToDTO(card);
    }
}
