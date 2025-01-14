package com.lewyonq.flashqi.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lewyonq.flashqi.deck.Deck;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @ManyToMany(mappedBy = "cards")
    @JsonIgnore
    private List<Deck> decks;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public List<Deck> getDecks() {
        return decks;
    }
}
