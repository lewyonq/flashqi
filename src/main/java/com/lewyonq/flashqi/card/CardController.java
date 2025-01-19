package com.lewyonq.flashqi.card;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getCards() {
        return ResponseEntity.ok(cardService.getCards());
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO) {
        cardService.saveCard(cardDTO);
        return ResponseEntity.created(URI.create("/api/v1/cards/" + cardDTO.getId())).body(cardDTO);
    }

    @PostMapping("/{cardId}/decks/{deckId}")
    public ResponseEntity<CardDTO> addToDeck(@PathVariable Long cardId, @PathVariable Long deckId) {
        return ResponseEntity.ok(cardService.addCardToDeck(cardId, deckId));
    }
}
