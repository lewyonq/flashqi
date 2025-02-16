package com.lewyonq.flashqi.card;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.lewyonq.flashqi.deck.DeckRepository;
import com.lewyonq.flashqi.exception.CardNotFoundException;

class CardServiceTest {
    
    @Mock
    private CardRepository cardRepository;

    @Mock
    private DeckRepository deckRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardService cardService;

    private CardDTO testCard;
    private Card savedCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCard = new CardDTO();
        testCard.setId(1L);
        testCard.setQuestion("Test Question");
        testCard.setAnswer("Test Answer");
        savedCard = new Card();
        savedCard.setId(1L);
        savedCard.setQuestion("Test Question");
        savedCard.setAnswer("Test Answer");
    }

    @Test
    void saveCard_WhenValidInput_ShouldSaveAndReturnCard() {
        when(cardMapper.mapToCard(testCard)).thenReturn(savedCard);
        when(cardRepository.save(savedCard)).thenReturn(savedCard);
        when(cardMapper.mapToDTO(savedCard)).thenReturn(testCard);

        CardDTO result = cardService.saveCard(testCard);
        
        assertNotNull(result);
        assertEquals(testCard.getId(), result.getId());
        assertEquals(testCard.getQuestion(), result.getQuestion());
        assertEquals(testCard.getAnswer(), result.getAnswer());

        verify(cardMapper).mapToCard(testCard);
        verify(cardRepository).save(savedCard);
        verify(cardMapper).mapToDTO(savedCard);
    }

    @Test
    void saveCard_WhenEmptyFields_ShouldSaveWithEmptyFields() {
        CardDTO emptyCard = new CardDTO();
        Card mappedEmptyCard = new Card();
        Card savedEmptyCard = new Card();
        savedEmptyCard.setId(1L);
        
        when(cardMapper.mapToCard(emptyCard)).thenReturn(mappedEmptyCard);
        when(cardRepository.save(mappedEmptyCard)).thenReturn(savedEmptyCard);
        when(cardMapper.mapToDTO(savedEmptyCard)).thenReturn(emptyCard);

        CardDTO result = cardService.saveCard(emptyCard);

        assertNotNull(result);
        assertNull(result.getQuestion());
        assertNull(result.getAnswer());
        
        verify(cardMapper).mapToCard(emptyCard);
        verify(cardRepository).save(mappedEmptyCard);
        verify(cardMapper).mapToDTO(savedEmptyCard);
    }

    @Test
    void saveCard_WhenRepositoryThrowsException_ShouldPropagateException() {
        when(cardMapper.mapToCard(testCard)).thenReturn(savedCard);
        when(cardRepository.save(savedCard)).thenThrow(new RuntimeException("Database error"));

        try {
            cardService.saveCard(testCard);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Database error", e.getMessage());
            verify(cardMapper).mapToCard(testCard);
            verify(cardRepository).save(savedCard);
            verify(cardMapper, never()).mapToDTO(any(Card.class));
        }
    }

    @Test
    void getCards_ShouldReturnCards() {
        when(cardRepository.findAll()).thenReturn(List.of(savedCard));
        when(cardMapper.mapToDTO(savedCard)).thenReturn(testCard);
        List<CardDTO> result = cardService.getCards();
        assertEquals(result, List.of(testCard));
    }

    @Test
    void getCards_WhenNoCards_ShouldReturnEmptyList() {
        when(cardRepository.findAll()).thenReturn(List.of());
        List<CardDTO> result = cardService.getCards();
        assertTrue(result.isEmpty());
        verify(cardMapper, never()).mapToDTO(any());
    }

    @Test
    void getCards_WhenMultipleCards_ShouldReturnAllCards() {
        Card secondCard = new Card();
        secondCard.setId(2L);
        CardDTO secondCardDTO = new CardDTO();
        secondCardDTO.setId(2L);

        when(cardRepository.findAll()).thenReturn(List.of(savedCard, secondCard));
        when(cardMapper.mapToDTO(savedCard)).thenReturn(testCard);
        when(cardMapper.mapToDTO(secondCard)).thenReturn(secondCardDTO);

        List<CardDTO> result = cardService.getCards();
        
        assertEquals(2, result.size());
        assertEquals(List.of(testCard, secondCardDTO), result);
        verify(cardMapper, times(2)).mapToDTO(any());
    }

    @Test
    void getCards_WhenRepositoryThrowsException_ShouldPropagateException() {
        when(cardRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cardService.getCards();
        });
        
        assertEquals("Database error", exception.getMessage());
        verify(cardMapper, never()).mapToDTO(any());
    }

    @Test
    void updateCardById_WhenValidInput_ShouldUpdateAndReturnCard() {
        Long cardId = 1L;
        CardDTO updateDTO = new CardDTO();
        updateDTO.setQuestion("Updated Question");
        updateDTO.setAnswer("Updated Answer");
        
        Card existingCard = new Card();
        existingCard.setId(cardId);
        existingCard.setQuestion("Old Question");
        existingCard.setAnswer("Old Answer");
        
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(existingCard));
        when(cardRepository.save(existingCard)).thenReturn(existingCard);
        when(cardMapper.mapToDTO(existingCard)).thenReturn(updateDTO);

        CardDTO result = cardService.updateCardById(cardId, updateDTO);

        assertNotNull(result);
        assertEquals("Updated Question", existingCard.getQuestion());
        assertEquals("Updated Answer", existingCard.getAnswer());
        verify(cardRepository).findById(cardId);
        verify(cardRepository).save(existingCard);
        verify(cardMapper).mapToDTO(existingCard);
    }

    @Test
    void updateCardById_WhenCardNotFound_ShouldThrowException() {
        Long cardId = 1L;
        CardDTO updateDTO = new CardDTO();
        
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> {
            cardService.updateCardById(cardId, updateDTO);
        });
        
        verify(cardRepository).findById(cardId);
        verify(cardRepository, never()).save(any());
        verify(cardMapper, never()).mapToDTO(any());
    }

    @Test
    void updateCardById_WhenNullFields_ShouldNotUpdateFields() {
        Long cardId = 1L;
        CardDTO updateDTO = new CardDTO();
        updateDTO.setQuestion(null);
        updateDTO.setAnswer(null);
        
        Card existingCard = new Card();
        existingCard.setId(cardId);
        existingCard.setQuestion("Original Question");
        existingCard.setAnswer("Original Answer");
        
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(existingCard));
        when(cardRepository.save(existingCard)).thenReturn(existingCard);
        when(cardMapper.mapToDTO(existingCard)).thenReturn(updateDTO);

        cardService.updateCardById(cardId, updateDTO);

        assertEquals("Original Question", existingCard.getQuestion());
        assertEquals("Original Answer", existingCard.getAnswer());
        verify(cardRepository).save(existingCard);
    }

    @Test
    void updateCardById_WhenEmptyStrings_ShouldTrimAndUpdate() {
        Long cardId = 1L;
        CardDTO updateDTO = new CardDTO();
        updateDTO.setQuestion("  Trimmed Question  ");
        updateDTO.setAnswer("  Trimmed Answer  ");
        
        Card existingCard = new Card();
        existingCard.setId(cardId);
        
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(existingCard));
        when(cardRepository.save(existingCard)).thenReturn(existingCard);
        when(cardMapper.mapToDTO(existingCard)).thenReturn(updateDTO);

        cardService.updateCardById(cardId, updateDTO);

        assertEquals("Trimmed Question", existingCard.getQuestion());
        assertEquals("Trimmed Answer", existingCard.getAnswer());
        verify(cardRepository).save(existingCard);
    }

    @Test
    void updateCardById_WhenPartialUpdate_ShouldUpdateOnlyProvidedFields() {
        Long cardId = 1L;
        CardDTO updateDTO = new CardDTO();
        updateDTO.setQuestion("New Question");
        
        Card existingCard = new Card();
        existingCard.setId(cardId);
        existingCard.setQuestion("Old Question");
        existingCard.setAnswer("Original Answer");
        
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(existingCard));
        when(cardRepository.save(existingCard)).thenReturn(existingCard);
        when(cardMapper.mapToDTO(existingCard)).thenReturn(updateDTO);

        cardService.updateCardById(cardId, updateDTO);

        assertEquals("New Question", existingCard.getQuestion());
        assertEquals("Original Answer", existingCard.getAnswer());
        verify(cardRepository).save(existingCard);
    }
}