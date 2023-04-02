package microservices.book.gamification.domain.badge;


import java.util.HashMap;
import java.util.Map;

/**
 * Enuemeration with the different types of Badges that a user can win.
 */
public enum ScoreBadge implements Badge{

    // Badges depending on score
    BRONZE_MULTIPLICATOR(100),
    SILVER_MULTIPLICATOR(500),
    GOLD_MULTIPLICATOR(1000);

    private final Integer points;

    private static final Map<Integer, ScoreBadge> BY_SCORE = new HashMap<>();

    private ScoreBadge(Integer points) {
        this.points = points;
    }

    static {
        for (ScoreBadge scoreBadge : values()){
            BY_SCORE.put(scoreBadge.points, scoreBadge);
        }
    }

    public int getPoints(){
        return this.points;
    }
}
