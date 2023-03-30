package microservices.book.gamification.controller;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.service.impl.LeaderBoardServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

    private static final Logger logger = LoggerFactory.getLogger(LeaderBoardController.class);

    private final LeaderBoardServiceImpl leaderBoardService;

    @Autowired
    public LeaderBoardController(LeaderBoardServiceImpl leaderBoardService){
        this.leaderBoardService = leaderBoardService;
    }


    @GetMapping
    ResponseEntity<List<LeaderBoardRow>> getLeaderboard() throws MicroServiceException {
        return ResponseEntity.ok(leaderBoardService.getCurrentLeaderBoard());
    }

}
