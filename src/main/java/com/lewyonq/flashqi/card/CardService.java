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

    public CardDTO getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
        CardDTO cardDTO = cardMapper.mapToDTO(card);
        return cardDTO;
    }

    public CardDTO updateCardById(Long cardId, CardDTO cardDTO) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        if (cardDTO.getQuestion() != null) {
            String question = cardDTO.getQuestion().trim();
            card.setQuestion(question);
        }

        if (cardDTO.getAnswer() != null) {
            String answer = cardDTO.getAnswer().trim();
            card.setAnswer(answer);
        }
        
        cardRepository.save(card);
        return cardMapper.mapToDTO(card);
    }

    public void deleteCardById(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new CardNotFoundException(cardId);
        }

        try {
            cardRepository.deleteById(cardId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete card with ID: " + cardId);
        }
    }

    public CardDTO addCardToDeck(Long cardId, Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException(deckId));

        if (deck.getCards().stream().anyMatch(c -> c.getId().equals(cardId))) {
            throw new IllegalStateException("This card is already added to deck " + deck.getTitle() + "!");
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        deck.getCards().add(card);
        deckRepository.save(deck);

        return cardMapper.mapToDTO(card);
    }
}
