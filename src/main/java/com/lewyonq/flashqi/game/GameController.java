package com.lewyonq.flashqi.game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    @PostMapping("/new")
    public void startGame(@RequestBody GameRequest gameRequest) {
        this.gameService.saveGame(gameRequest);
    }

    @PutMapping("/{id}/update-result")
    public Game updateGame(Long howManyCardsPassed,Long howManyCardsFailed, Long gameId) {
        return this.gameService.updateResult(howManyCardsPassed, howManyCardsFailed, gameId);
    }
}
