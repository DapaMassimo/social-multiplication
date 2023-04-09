package microservices.book.gamification.repository;

import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.domain.LeaderBoardRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

    /**
     * Gets the total score for a given user, being the sum of
     * the scores of all his ScoreCards.
     * @param userId the id of the user for which the total score should be retrieved
     * @return the total score of the given user
     */
    @Query(value = "SELECT SUM(SCORE) FROM SCORE_CARD WHERE USER_ID = :userId", nativeQuery = true)
    Integer getTotalScoreForUser(Long userId);


    /**
     * Retrieves a list of {@link LeaderBoardRow}s
     * representing the Leader Board of users and their total score.
     * @return the leader board, sorted by highest score first.
     */
    @Query(value = "SELECT USER_ID as userId, SUM(SCORE) as totalScore" +
            "FROM SCORE_CARD " +
            "GROUP BY USER_ID ORDER BY SUM(SCORE) DESC", nativeQuery = true)
    List<LeaderBoardRow> findFirst10();

    /**
     * Retrieves all the ScoreCards for a given user,
     * identified by his user id.
     * @param userId the id of the user
     * @return a list containing all the ScoreCards for the given user,
     * sorted by most recent.
     */
    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
