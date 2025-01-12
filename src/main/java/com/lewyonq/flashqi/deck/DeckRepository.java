package com.lewyonq.flashqi.deck;

import com.lewyonq.flashqi.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Card, Long> {
}
