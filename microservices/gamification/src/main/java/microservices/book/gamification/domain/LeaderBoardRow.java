package microservices.book.gamification.domain;

import java.io.Serializable;

/**
 * A position in the leaderboard that is the total score together with the user.
 */
public final class LeaderBoardRow implements Serializable {

    private static final long serialVersionUID = 8110573614685298184L;

    private final Long userId;
    private final long totalScore;

    public LeaderBoardRow() {
        this(0L, 0);
    }

    public LeaderBoardRow(Long userId, long totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
    }
}
