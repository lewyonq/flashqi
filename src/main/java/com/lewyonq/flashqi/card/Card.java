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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        if (answer == null) {
            throw new IllegalArgumentException("Answer cannot be null");
        }
        this.answer = answer;
    }

    public List<Deck> getDecks() {
        return decks;
    }
}
