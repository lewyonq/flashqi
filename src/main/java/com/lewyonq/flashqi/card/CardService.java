package com.lewyonq.flashqi.card;

import com.lewyonq.flashqi.deck.Deck;
import com.lewyonq.flashqi.deck.DeckService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final DeckService deckService;

    @Transactional
    public Card saveCard(CardRequest cardRequest) {
        Deck deck = deckService.getDeckById(cardRequest.getDeckId());
        try {
            Card card = cardMapper.mapRequestToEntity(cardRequest, deck);
            return this.cardRepository.save(card);
        } catch (DataAccessException dae) {
            throw new RuntimeException("Failed to save card", dae);
        }
    }

    @Transactional
    public Card updateCard(Long id, CardRequest cardRequest) {
        Card card = this.getCardById(id);
        this.cardMapper.updateEntityFromRequest(card, cardRequest);
        return this.cardRepository.save(card);
    }

    @Transactional
    public void deleteCard(Long id) {
        this.cardRepository.deleteById(id);
    }

    public Card getCardById(Long id) {
        return this.cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));
    }

}
