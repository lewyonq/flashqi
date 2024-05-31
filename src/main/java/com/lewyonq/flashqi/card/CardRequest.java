package com.lewyonq.flashqi.card;

import lombok.Data;

@Data
public class CardRequest {
    private String question;
    private String answer;
    private Long deckId;
}
