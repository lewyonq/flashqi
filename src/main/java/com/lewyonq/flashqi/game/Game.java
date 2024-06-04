package com.lewyonq.flashqi.game;

import com.lewyonq.flashqi.deck.Deck;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Deck deck;

    private Long howManyCardsPassed = 0L;
    private Long howManyCardsFailed = 0L;
    private Double resultInPercent = 0D;
}
