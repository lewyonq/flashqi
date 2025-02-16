package com.lewyonq.flashqi.deck;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lewyonq.flashqi.card.CardDTO;

@WebMvcTest(DeckController.class)
public class DeckControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeckService deckService;

    @Autowired
    private ObjectMapper objectMapper;

    private DeckDTO testDeck;

    @BeforeEach
    void SetUp() {
        testDeck = new DeckDTO();
        testDeck.setTitle("Test name");
        testDeck.setDescription("Test description");
    }

    @Test
    void shouldCreateDeck() throws Exception {
        when(deckService.saveDeck(any(DeckDTO.class))).thenReturn(testDeck);

        mockMvc.perform(post("/api/v1/decks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(testDeck)))
        .andExpect(status().isCreated());
    }

    @Test
    void shouldGetDecks() throws Exception {
        List<DeckDTO> decks = List.of(testDeck);
        when(deckService.getDecks()).thenReturn(decks);

        mockMvc.perform(get("/api/v1/decks"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Test name"))
        .andExpect(jsonPath("$[0].description").value("Test description"));
    }

    @Test
    void shouldAddCardToDeck() throws Exception {
        Long deckId = 1L;
        CardDTO cardDTO = new CardDTO();
        cardDTO.setQuestion("Test question");
        cardDTO.setAnswer("Test answer");
        
        CardDTO savedCard = new CardDTO();
        savedCard.setId(1L);
        savedCard.setQuestion("Test question");
        savedCard.setAnswer("Test answer");
        
        when(deckService.addCardToDeck(eq(deckId), any(CardDTO.class))).thenReturn(savedCard);
        
        mockMvc.perform(post("/api/v1/decks/{deckId}/create-card", deckId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cardDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value("Test question"))
                .andExpect(jsonPath("$.answer").value("Test answer"));
    }
}
