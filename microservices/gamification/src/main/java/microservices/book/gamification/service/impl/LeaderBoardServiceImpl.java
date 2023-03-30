package microservices.book.gamification.service.impl;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.repository.ScoreCardRepository;
import microservices.book.gamification.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    @Autowired
    private ScoreCardRepository scoreCardRepository;
    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() throws MicroServiceException {
        return scoreCardRepository.findFirst10();
    }
}
