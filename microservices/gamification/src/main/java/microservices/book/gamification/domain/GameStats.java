package microservices.book.gamification.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This object contains the result of one or many iterations of the game.
 * It may contain any combination of {@link ScoreCard} objects and
 * {@link microservices.book.gamification.domain.BadgeCard} objects.
 *
 * It can be used as a delta (as a single game iteration) or to represent
 * the total amount of score/badges.
 */

/**
 * Score and badges for a given user.
 * It can be used for a given game iteration (one attempt's result)
 * or for a collection of attempts (aggregating score and badges).
 */
public final class GameStats implements Serializable {

    private static final long serialVersionUID = -3791295672683951508L;

    private final Long userId;
    private final long score;
    private final List<Badge> badgeList;

    public GameStats(Long userId, long score, List<Badge> badgeList) {
        this.userId = userId;
        this.score = score;
        this.badgeList = badgeList;
    }

    public GameStats() {
        this(0L, 0, new ArrayList<>());
    }

    /**
     * Factory method to build an empty instance (zero points
     * and no badges)
     * @param userId the user's id
     * @return a {@link GameStats} object with zero score and
     * no badges.
     */
    public GameStats(Long userId){
        this(userId, 0, Collections.emptyList());
    }

    /**
     * Factory method to build an empty instance (zero points and no badges)
     * @param userId the user's id
     * @return a {@link GameStats} object with zero score and no badges
     */
    public static GameStats emptyStats(final Long userId) {
        return new GameStats(userId, 0, Collections.emptyList());
    }


    public Long getUserId() {
        return userId;
    }

    public long getScore() {
        return score;
    }

    /**
     *
     * @return an unmodifiable view of the badge cards list
     */
    public List<Badge> getBadgeList() {
        return Collections.unmodifiableList(badgeList);
    }
}
