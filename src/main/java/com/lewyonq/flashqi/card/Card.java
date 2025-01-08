package com.lewyonq.flashqi.card;

import com.lewyonq.flashqi.deck.Deck;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @ManyToMany(mappedBy = "cards")
    private List<Deck> decks;
}
