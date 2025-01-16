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
    public ResponseEntity<List<Deck>> getDecks() {
        return ResponseEntity.ok(deckService.getDecks());
    }

    @PostMapping
    public ResponseEntity<Deck> saveDeck(@RequestBody DeckDTO deckDTO) {
        Deck savedDeck = deckService.saveDeck(mapToDeck(deckDTO));
        return ResponseEntity.created(URI.create("api/v1/decks/" + savedDeck.getId())).body(savedDeck);
    }

    @PostMapping("/{deckId}/create-card")
    public ResponseEntity<Card> addCardToDeck(@PathVariable Long deckId, @RequestBody CardDTO cardDTO) {
        Card card = cardService.saveCard(cardDTO);
        Card cardWithDeck = deckService.addCardToDeck(deckId, card);
        return ResponseEntity.created(URI.create("api/v1/decks" + deckId + "/cards" + cardWithDeck.getId()))
                .body(cardWithDeck);
    }

    private Deck mapToDeck(DeckDTO deckDTO) {
        Deck deck = new Deck();
        deck.setName(deckDTO.getName());
        deck.setDescription(deckDTO.getDescription());
        return deck;
    }
}
