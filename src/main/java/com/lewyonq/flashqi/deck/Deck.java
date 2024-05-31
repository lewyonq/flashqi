package com.lewyonq.flashqi.deck;

import com.lewyonq.flashqi.card.Card;
import com.lewyonq.flashqi.game.Game;
import com.lewyonq.flashqi.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    private User user;

    @Enumerated
    private List<DeckCategory> deckCategories;

    @ManyToMany
    private List<CustomCategory> customCategories;

    @Builder.Default
    @OneToMany
    private List<Card> cards = new ArrayList<>();

    @Builder.Default
    @OneToMany
    private List<Game> games = new ArrayList<>();
}
