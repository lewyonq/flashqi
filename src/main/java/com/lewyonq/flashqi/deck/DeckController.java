package com.lewyonq.flashqi.deck;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {
    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public List<Deck> getDecks() {
        return deckService.getDecks();
    }

    @PostMapping("/add")
    public Deck saveDeck(@RequestBody Deck deck) {
        return deckService.saveDeck(deck);
    }
}
