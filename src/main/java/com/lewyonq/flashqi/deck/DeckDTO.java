package com.lewyonq.flashqi.deck;

import java.util.List;

import com.lewyonq.flashqi.card.CardDTO;

public class DeckDTO {
    private Long id;
    private String name;
    private String description;
    private List<CardDTO> cards;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<CardDTO> getCards() {
        return cards;
    }
    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }
}
