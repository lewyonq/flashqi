package com.lewyonq.flashqi.deck;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lewyonq.flashqi.card.Card;

@Service
public class DeckService {
    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public DeckDTO saveDeck(DeckDTO deckDTO) {
        Deck deck = mapToDeck(deckDTO);
        return mapToDTO(deckRepository.save(deck));
    }

    public List<DeckDTO> getDecks() {
        return deckRepository.findAll()
            .stream()
            .map(this::mapToDTO)
            .toList();
    }

    public DeckDTO addCardToDeck(Long deckId, Card card) {
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        deck.getCards().add(card);
        deckRepository.save(deck);

        return mapToDTO(deck);
    }

    private Deck mapToDeck(DeckDTO deckDTO) {
        Deck deck = new Deck();
        deck.setName(deckDTO.getName());
        deck.setDescription(deckDTO.getDescription());
        return deck;
    }

    private DeckDTO mapToDTO(Deck deck) {
        DeckDTO deckDTO = new DeckDTO();
        deckDTO.setId(deck.getId());
        deckDTO.setName(deck.getName());
        deckDTO.setDescription(deck.getDescription());
        return deckDTO;
    }
}
