package com.lewyonq.flashqi.deck;

import com.lewyonq.flashqi.card.Card;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany()
    @JoinTable(
            name = "deck_card",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards;
}
