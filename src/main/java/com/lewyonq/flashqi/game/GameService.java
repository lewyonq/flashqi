package com.lewyonq.flashqi.game;

import com.lewyonq.flashqi.deck.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final DeckService deckService;

    public Game saveGame(GameRequest gameRequest) {
        Game game = Game.builder()
                .deck(this.deckService.getDeckById(gameRequest.getDeckId()))
                .build();
        return this.gameRepository.save(game);
    }

    public Game getGameById(Long id) {
        return this.gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found!"));
    }

    public Game updateResult(Long howManyCardsPassed,Long howManyCardsFailed, Long gameId) {
        Game game = this.getGameById(gameId);
        game.setHowManyCardsPassed(howManyCardsPassed);
        game.setHowManyCardsFailed(howManyCardsFailed);
        return this.gameRepository.save(game);
    }

    private Double countResult(Long howManyCardsPassed,Long howManyCardsFailed) {
        Double result = Double.valueOf(howManyCardsPassed / (howManyCardsPassed + howManyCardsFailed)) * 100;
        return result; //todo: dodac zaokraglanie do 2 miejsc
    }
}
