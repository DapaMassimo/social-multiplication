package microservices.book.gamification.controller;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.service.impl.GameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class UserStatsController {

    private static final Logger logger = LoggerFactory.getLogger(UserStatsController.class);

    private final GameServiceImpl gameService;

    public UserStatsController(GameServiceImpl gameService){
        this.gameService = gameService;
    }

    @GetMapping
    ResponseEntity<GameStats> getUserStats(@RequestParam("userId") String userId) throws MicroServiceException {
        return ResponseEntity.ok(gameService.retrieveStatsForUser(Long.parseLong(userId)));
    }
}
