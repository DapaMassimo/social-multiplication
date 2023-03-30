package microservices.book.gamification.service;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.domain.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {

    /**
     * Retrieves the current leader board with the top score users
     * @return the 10 users with the highest score
     */
    List<LeaderBoardRow> getCurrentLeaderBoard() throws MicroServiceException;
}
