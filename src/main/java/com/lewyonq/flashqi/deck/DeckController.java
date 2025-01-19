package com.lewyonq.flashqi.deck;

import com.lewyonq.flashqi.card.Card;
import com.lewyonq.flashqi.card.CardDTO;
import com.lewyonq.flashqi.card.CardService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/decks")
public class DeckController {
    private final DeckService deckService;
    private final CardService cardService;

    public DeckController(DeckService deckService, CardService cardService) {
        this.deckService = deckService;
        this.cardService = cardService;
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
        CardDTO savedCard = cardService.saveCard(cardDTO);
        CardDTO cardWithDeck = deckService.addCardToDeck(deckId, savedCard);
        return ResponseEntity.created(URI.create("api/v1/decks" + deckId + "/cards" + cardWithDeck.getId()))
                .body(cardWithDeck);
    }
}
