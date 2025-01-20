package com.lewyonq.flashqi.deck;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lewyonq.flashqi.card.CardDTO;

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
    public ResponseEntity<DeckDTO> saveDeck(@RequestBody DeckDTO deckDTO) {
        DeckDTO savedDeck = deckService.saveDeck(deckDTO);
        return ResponseEntity.created(URI.create("api/v1/decks/" + savedDeck.getId())).body(savedDeck);
    }

    @PostMapping("/{deckId}/create-card")
    public ResponseEntity<CardDTO> addCardToDeck(@PathVariable Long deckId, @RequestBody CardDTO cardDTO) {
        CardDTO cardWithDeck = deckService.addCardToDeck(deckId, cardDTO);
        return ResponseEntity.created(URI.create("api/v1/decks" + deckId + "/cards" + cardWithDeck.getId()))
                .body(cardWithDeck);
    }
}
