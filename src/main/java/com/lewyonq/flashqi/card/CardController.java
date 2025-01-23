package com.lewyonq.flashqi.card;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lewyonq.flashqi.exception.CardNotFoundException;
import com.lewyonq.flashqi.exception.DeckNotFoundException;

import jakarta.validation.Valid;

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

    @GetMapping("/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable Long cardId) {
        try {
            return ResponseEntity.ok(cardService.getCardById(cardId));
        } catch (CardNotFoundException cardNotFoundException){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardDTO> updateCardById(@PathVariable Long cardId, @RequestBody @Valid CardDTO cardDTO) {
        try {
            CardDTO updatedCard = cardService.updateCardById(cardId, cardDTO);
            return ResponseEntity.ok(updatedCard);
        } catch (CardNotFoundException cardNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody @Valid CardDTO cardDTO) {
        try {
            cardService.saveCard(cardDTO);
            return ResponseEntity.created(URI.create("/api/v1/cards/" + cardDTO.getId())).body(cardDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        try {
            cardService.deleteCardById(cardId);
            return ResponseEntity.noContent().build();
        } catch (CardNotFoundException cardNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cardId}/decks/{deckId}")
    public ResponseEntity<CardDTO> addToDeck(@PathVariable Long cardId, @PathVariable Long deckId) {
        try {
            return ResponseEntity.ok(cardService.addCardToDeck(cardId, deckId));
        } catch (CardNotFoundException cardNotFoundException) {
            return ResponseEntity.notFound().build();
        } catch (DeckNotFoundException deckNotFoundException) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException illegalStateException) {
            return ResponseEntity.badRequest().build();
        }
    }
}
