package microservices.book.gamification.domain.badge;

/**
 * Enuemeration with the different types of Badges that a user can win.
 */
public enum ScoreBadge implements Badge{

    // Badges depending on score
    BRONZE_MULTIPLICATOR(100),
    SILVER_MULTIPLICATOR(500),
    GOLD_MULTIPLICATOR(1000);

    private final Integer points;

    ScoreBadge(Integer points) {
        this.points = points;
    }


    @Override
    public Integer getPoints() {
        return this.points;
    }
}
