package microservices.book.gamification.domain.badge;


import java.util.HashMap;
import java.util.Map;

/**
 * Badges won for conditions other than score.
 */
public enum SpecialBadge implements Badge{
    FIRST_ATTEMPT("FIRST_ATTEMPT"),
    FIRST_WON("FIRST_WON"),
    LUCKY_NUMBER("LUCKY_NUMBER");

    private final String description;

    private SpecialBadge(String description){
        this.description = description;
    }

    private static final Map<String, SpecialBadge> BY_DESCRIPTION = new HashMap<>();


    static {
        for (SpecialBadge specialBadge : values()){
            BY_DESCRIPTION.put(specialBadge.description, specialBadge);
        }
    }

    public String getDescription(){
        return String.copyValueOf(description.toCharArray());
    }

}
