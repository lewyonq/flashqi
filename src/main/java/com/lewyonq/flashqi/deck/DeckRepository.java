package com.lewyonq.flashqi.deck;

import com.lewyonq.flashqi.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Card, Long> {
}
