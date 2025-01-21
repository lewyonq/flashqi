package com.lewyonq.flashqi.deck;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lewyonq.flashqi.card.Card;
import com.lewyonq.flashqi.card.CardDTO;
import com.lewyonq.flashqi.card.CardMapper;
import com.lewyonq.flashqi.card.CardRepository;

@Service
public class DeckService {
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final DeckMapper deckMapper;
    private final CardMapper cardMapper;

    public DeckService(
        DeckRepository deckRepository,
        CardRepository cardRepository,
        DeckMapper deckMapper,
        CardMapper cardMapper) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
        this.deckMapper = deckMapper;
        this.cardMapper = cardMapper;
    }

    public DeckDTO saveDeck(DeckDTO deckDTO) {
        Deck deck = deckMapper.mapToDeck(deckDTO);
        return deckMapper.mapToDTO(deckRepository.save(deck));
    }

    public List<DeckDTO> getDecks() {
        return deckRepository.findAll()
            .stream()
            .map(deckMapper::mapToDTO)
            .toList();
    }

    public CardDTO addCardToDeck(Long deckId, CardDTO cardDTO) {
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        Card card = cardMapper.mapToCard(cardDTO);
        card = cardRepository.save(card);

        deck.getCards().add(card);
        deckRepository.save(deck);

        return cardMapper.mapToDTO(card);
    }
}
