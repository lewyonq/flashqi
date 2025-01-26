package com.lewyonq.flashqi.deck;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lewyonq.flashqi.card.CardDTO;
import com.lewyonq.flashqi.exception.DeckNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/decks")
public class DeckController {
    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public ResponseEntity<List<DeckDTO>> getDecks() {
        return ResponseEntity.ok(deckService.getDecks());
    }

    @PostMapping
    public ResponseEntity<DeckDTO> createDeck(@RequestBody @Valid DeckDTO deckDTO) {
        DeckDTO savedDeck = deckService.saveDeck(deckDTO);
        return ResponseEntity.created(URI.create("api/v1/decks/" + savedDeck.getId())).body(savedDeck);
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckDTO> getDeckById(@PathVariable Long deckId) {
        try {
            return ResponseEntity.ok(deckService.getDeckById(deckId));
        } catch (DeckNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{deckId}")
    public ResponseEntity<DeckDTO> updateDeckById(@PathVariable Long deckId, @RequestBody @Valid DeckDTO deckDTO) {
        try {
            DeckDTO updatedDeck = deckService.updateDeckById(deckId, deckDTO);
            return ResponseEntity.ok(updatedDeck);
        } catch (DeckNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{deckId}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long deckId) {
        try {
            deckService.deleteDeckById(deckId);
            return ResponseEntity.noContent().build();
        } catch (DeckNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{deckId}/create-card")
    public ResponseEntity<CardDTO> addCardToDeck(@PathVariable Long deckId, @RequestBody @Valid CardDTO cardDTO) {
        CardDTO cardWithDeck = deckService.addCardToDeck(deckId, cardDTO);
        return ResponseEntity.created(URI.create("api/v1/decks" + deckId + "/cards" + cardWithDeck.getId()))
                .body(cardWithDeck);
    }
}
