package microservices.book.gamification.domain.badge;


/**
 * Badges won for conditions other than score.
 */
public enum SpecialBadge implements Badge{
    FIRST_ATTEMPT,
    FIRST_WON,
    LUCKY_NUMBER;

    @Override
    public Integer getPoints() {
        return null;
    }

}
