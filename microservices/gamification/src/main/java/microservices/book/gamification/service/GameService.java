package microservices.book.gamification.service;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.domain.GameStats;

public interface GameService {

    /**
     * Process a new attempt from a given user.
     *
     * @param userId    the user's unique id
     * @param attemptId the attemptId, can be used to retrieve extra data if needed
     * @param correct   indicates if the attempt was correct
     * @return a {@link GameStats} object containing the NEW score and badge cards (if any) obtained
     * with this attempt
     */
    GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) throws MicroServiceException;

    /**
     * Gets the game statistics for a given user
     * @param userId the user's unique id
     * @return a {@link GameStats} object containing the TOTAL score and badge cards ever obtained
     */
    GameStats retrieveStatsForUser(Long userId) throws MicroServiceException;
}
