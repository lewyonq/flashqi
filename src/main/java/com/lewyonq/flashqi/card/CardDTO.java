package com.lewyonq.flashqi.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CardDTO {
    private Long id;

    @NotBlank(message = "Question cannot be blank")
    @Size(min = 1, max = 500, message = "Question must be between 1 and 500 characters")
    private String question;

    @NotBlank(message = "Answer cannot be blank")
    @Size(min = 1, max = 500, message = "Answer must be between 1 and 500 characters")
    private String answer; 

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
}