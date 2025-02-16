package com.lewyonq.flashqi.card;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lewyonq.flashqi.exception.CardNotFoundException;
import com.lewyonq.flashqi.exception.DeckNotFoundException;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardService cardService;

    @Autowired
    private ObjectMapper objectMapper;

    private CardDTO testCard;
    
    @BeforeEach
    void setUp() {
        testCard = new CardDTO();
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
    void getCardById_WhenCardExists_ShouldReturnCard() throws Exception {
        Long cardId = 1L;
        testCard.setId(cardId);
        when(cardService.getCardById(cardId)).thenReturn(testCard);

        mockMvc.perform(get("/api/v1/cards/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.question").value("Test Question"))
               .andExpect(jsonPath("$.answer").value("Test Answer"));
    }

    @Test
    void getCardById_WhenCardNotFound_ShouldReturnNotFound() throws Exception {
        Long cardId = 1L;
        testCard.setId(cardId);
        when(cardService.getCardById(cardId)).thenThrow(new CardNotFoundException(cardId));
        
        mockMvc.perform(get("/api/v1/cards/" + cardId))
            .andExpect(status().isNotFound());
    }

    @Test
    void updateCardById_WhenCardExists_ShouldReturnUpdatedCard() throws Exception {
        Long cardId = 1L;
        CardDTO updatedCard = new CardDTO();
        updatedCard.setId(cardId);
        updatedCard.setQuestion("Updated Question");
        updatedCard.setAnswer("Updated Answer");
        
        when(cardService.updateCardById(eq(cardId), any(CardDTO.class))).thenReturn(updatedCard);

        mockMvc.perform(put("/api/v1/cards/" + cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCard)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardId))
                .andExpect(jsonPath("$.question").value("Updated Question"))
                .andExpect(jsonPath("$.answer").value("Updated Answer"));
    }

    @Test
    void updateCardById_WhenCardNotFound_ShouldReturnNotFound() throws Exception {
        Long cardId = 1L;
        CardDTO updatedCard = new CardDTO();
        updatedCard.setId(cardId);
        updatedCard.setQuestion("Updated Question");
        updatedCard.setAnswer("Updated Answer");
        
        when(cardService.updateCardById(eq(cardId), any(CardDTO.class)))
            .thenThrow(new CardNotFoundException(cardId));

        mockMvc.perform(put("/api/v1/cards/" + cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCard)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCard_WhenCardExists_ShouldReturnNoContent() throws Exception {
        Long cardId = 1L;
        doNothing().when(cardService).deleteCardById(cardId);

        mockMvc.perform(delete("/api/v1/cards/" + cardId))
                .andExpect(status().isNoContent());

        verify(cardService, times(1)).deleteCardById(cardId);
    }

    @Test
    void deleteCard_WhenCardNotFound_ShouldReturnNotFound() throws Exception {
        Long cardId = 1L;
        doThrow(new CardNotFoundException(cardId))
            .when(cardService).deleteCardById(cardId);

        mockMvc.perform(delete("/api/v1/cards/" + cardId))
                .andExpect(status().isNotFound());

        verify(cardService, times(1)).deleteCardById(cardId);
    }

    @Test
    void shouldGetAllCards() throws Exception {
        List<CardDTO> cards = Arrays.asList(testCard);
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

    @Test
    void addToDeck_WhenCardNotFound_ShouldReturnNotFound() throws Exception {
        Long cardId = 1L;
        Long deckId = 1L;

        when(cardService.addCardToDeck(cardId, deckId))
                .thenThrow(new CardNotFoundException(cardId));

        mockMvc.perform(post("/api/v1/cards/{cardId}/decks/{deckId}", cardId, deckId))
                .andExpect(status().isNotFound());
    }

    @Test
    void addToDeck_WhenDeckNotFound_ShouldReturnNotFound() throws Exception {
        Long cardId = 1L;
        Long deckId = 1L;

        when(cardService.addCardToDeck(cardId, deckId))
                .thenThrow(new DeckNotFoundException(deckId));

        mockMvc.perform(post("/api/v1/cards/{cardId}/decks/{deckId}", cardId, deckId))
                .andExpect(status().isNotFound());
    }

    @Test
    void addToDeck_WhenIllegalState_ShouldReturnBadRequest() throws Exception {
        Long cardId = 1L;
        Long deckId = 1L;

        when(cardService.addCardToDeck(cardId, deckId))
                .thenThrow(new IllegalStateException("Card already in deck"));

        mockMvc.perform(post("/api/v1/cards/{cardId}/decks/{deckId}", cardId, deckId))
                .andExpect(status().isBadRequest());
    }
}