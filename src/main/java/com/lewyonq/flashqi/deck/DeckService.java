package com.lewyonq.flashqi.deck;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lewyonq.flashqi.card.Card;
import com.lewyonq.flashqi.card.CardDTO;
import com.lewyonq.flashqi.card.CardMapper;
import com.lewyonq.flashqi.card.CardRepository;
import com.lewyonq.flashqi.exception.DeckNotFoundException;

@Service
public class DeckService {
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final DeckMapper deckMapper;
    private final CardMapper cardMapper;

    public DeckService(
        DeckRepository deckRepository,
        CardRepository cardRepository,
        DeckMapper deckMapper,
        CardMapper cardMapper) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
        this.deckMapper = deckMapper;
        this.cardMapper = cardMapper;
    }

    public DeckDTO saveDeck(DeckDTO deckDTO) {
        Deck deck = deckMapper.mapToDeck(deckDTO);
        return deckMapper.mapToDTO(deckRepository.save(deck));
    }

    public List<DeckDTO> getDecks() {
        return deckRepository.findAll()
            .stream()
            .map(deckMapper::mapToDTO)
            .toList();
    }

    public DeckDTO getDeckById(Long deckId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException(deckId));
        DeckDTO deckDTO = deckMapper.mapToDTO(deck);
        return deckDTO;
    }

    public DeckDTO updateDeckById(Long deckId, DeckDTO deckDTO) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException(deckId));
        
        if (deckDTO.getTitle() != null) {
            String title = deckDTO.getTitle().trim();
            deck.setTitle(title);            
        }
        if (deckDTO.getDescription() != null) {
            String description = deckDTO.getDescription().trim();
            deck.setDescription(description);            
        }

        deckRepository.save(deck);
        return deckMapper.mapToDTO(deck);
    }

    public void deleteDeckById(Long deckId) {
        if (!deckRepository.existsById(deckId)) {
            throw new DeckNotFoundException(deckId);
        }

        try {
            deckRepository.deleteById(deckId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete deck with ID: " + deckId);
        }
    }

    public CardDTO addCardToDeck(Long deckId, CardDTO cardDTO) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new DeckNotFoundException(deckId));
        Card card = cardMapper.mapToCard(cardDTO);
        card = cardRepository.save(card);

        deck.getCards().add(card);
        deckRepository.save(deck);

        return cardMapper.mapToDTO(card);
    }
}
