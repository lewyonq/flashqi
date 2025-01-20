package com.lewyonq.flashqi.card;

import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public Card mapToCard(CardDTO cardDTO) {
        Card card = new Card();
        card.setQuestion(cardDTO.getQuestion());
        card.setAnswer(cardDTO.getAnswer());
        return card;
    }

    public CardDTO mapToDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setAnswer(card.getAnswer());
        cardDTO.setQuestion(card.getQuestion());
        return cardDTO;
    }
}
