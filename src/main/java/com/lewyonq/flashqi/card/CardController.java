package com.lewyonq.flashqi.card;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping()
    public List<Card> getCards() {
        return cardService.getCards();
    }

    @PostMapping("/create")
    public Card create(@RequestBody Card card) {
        return cardService.saveCard(card);
    }

    @PostMapping("/{cardId}/add-to-deck")
    public Card addToDeck(@RequestBody Long deckId, @PathVariable Long cardId) {
        return cardService.addCardToDeck(cardId, deckId);
    }
}
