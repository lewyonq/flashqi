package com.lewyonq.flashqi.card;

import com.lewyonq.flashqi.deck.Deck;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    private Deck deck;

    private Long howManyWatched;
    private Long howManyPassed;
    private Long howManyFailed;
}
