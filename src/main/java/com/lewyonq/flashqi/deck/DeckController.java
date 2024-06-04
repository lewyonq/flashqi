package com.lewyonq.flashqi.deck;

import java.util.List;

import com.lewyonq.flashqi.card.Card;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/decks")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;

    @GetMapping
    public List<Deck> getAllDecks() {
        return this.deckService.getAllDecks();
    }

    @GetMapping("/{id}")
    public Deck getDeckById(@PathVariable Long id) {
        return this.deckService.getDeckById(id);
    }

    @GetMapping("/{id}/cards")
    public List<Card> getCards(@PathVariable Long id) {
        return this.deckService.getCardsForDeck(id);
    }

    @PostMapping("/add")
    public Deck createDeck(@RequestBody DeckRequest deckRequest) {
        return this.deckService.saveDeck(deckRequest);
    }

    @PutMapping("/edit/{id}")
    public Deck updateDeck(@PathVariable Long id, @RequestBody DeckRequest deckRequest) {
        return this.deckService.updateDeck(id, deckRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDeck(@PathVariable Long id) {
        this.deckService.deleteDeck(id);
    }
}
