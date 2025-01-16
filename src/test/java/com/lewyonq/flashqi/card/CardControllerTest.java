package com.lewyonq.flashqi.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardService cardService;

    @Autowired
    private ObjectMapper objectMapper;

    private Card testCard;

    @BeforeEach
    void setUp() {
        testCard = new Card();
        testCard.setQuestion("Test Question");
        testCard.setAnswer("Test Answer");
    }

    @Test
    void shouldCreateCard() throws Exception {
        when(cardService.saveCard(any(CardDTO.class))).thenReturn(testCard);

        mockMvc.perform(post("/api/v1/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCard)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question").value("Test Question"))
                .andExpect(jsonPath("$.answer").value("Test Answer"));
    }

    @Test
    void shouldGetAllCards() throws Exception {
        List<Card> cards = Arrays.asList(testCard);
        when(cardService.getCards()).thenReturn(cards);

        mockMvc.perform(get("/api/v1/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question").value("Test Question"))
                .andExpect(jsonPath("$[0].answer").value("Test Answer"));
    }

    @Test
    void shouldAddCardToDeck() throws Exception {
        Long cardId = 1L;
        Long deckId = 2L;

        when(cardService.addCardToDeck(cardId, deckId)).thenReturn(testCard);

        mockMvc.perform(post("/api/v1/cards/{cardId}/decks/{deckId}", cardId, deckId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Test Question"))
                .andExpect(jsonPath("$.answer").value("Test Answer"));
    }
} 