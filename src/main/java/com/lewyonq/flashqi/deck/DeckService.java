package com.lewyonq.flashqi.deck;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeckService {
    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;

    @Transactional
    public Deck saveDeck(DeckRequest deckRequest) {
        try {
            Deck deck = deckMapper.mapRequestToEntity(deckRequest);
            return this.deckRepository.save(deck);
        } catch (DataAccessException dae) {
            throw new RuntimeException("Failed to save deck", dae);
        }
    }

    @Transactional
    public Deck updateDeck(Long id, DeckRequest deckRequest) {
        Deck deck = this.getDeckById(id);
        deckMapper.updateEntityFromRequest(deck, deckRequest);
        return this.deckRepository.save(deck);
    }

    @Transactional
    public void deleteDeck(Long id) {
        this.deckRepository.deleteById(id);
    }

    public Deck getDeckById(Long id) {
        return this.deckRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Deck not found with id " + id));
    }

    public List<Deck> getAllDecks() {
        return this.deckRepository.findAll();
    }
}
