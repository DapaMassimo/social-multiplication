package microservices.book.gamification.domain;

/**
 * Enuemeration with the different types of Badges that a user can win.
 */
public enum Badge {
    // Badges won for conditions other than score
    FIRST_ATTEMPT(0),
    FIRST_WON(10),

    // Badges depending on score
    BRONZE_MULTIPLICATOR(100),
    SILVER_MULTIPLICATOR(500),
    GOLD_MULTIPLICATOR(1000);

    private final int points;

    Badge(int i) {
        this.points = i;
    }

    public int getPoints() {
        return points;
    }


}
