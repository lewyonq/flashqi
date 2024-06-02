package com.lewyonq.flashqi.card;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    @GetMapping("/{id}")
    public Card getCard(@PathVariable Long id) {
        return this.cardService.getCardById(id);
    }

    @PostMapping("/add")
    public Card createCard(@RequestBody CardRequest cardRequest) {
        return this.cardService.saveCard(cardRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }

    @PutMapping("/edit/{id}")
    public Card updateCard(@PathVariable Long id, @RequestBody CardRequest cardRequest) {
        return this.cardService.updateCard(id, cardRequest);
    }
}
