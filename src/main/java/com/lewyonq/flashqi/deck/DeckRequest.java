package com.lewyonq.flashqi.deck;

import lombok.Data;

import java.util.List;

@Data
public class DeckRequest {
    private String name;
    private String description;
    private List<DeckCategory> deckCategories;
    private List<CustomCategory> customCategories;
}
