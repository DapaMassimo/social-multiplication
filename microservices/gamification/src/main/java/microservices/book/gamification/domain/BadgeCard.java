package microservices.book.gamification.domain;

import microservices.book.gamification.domain.badge.jpa.BadgeConverter;
import microservices.book.gamification.domain.badge.Badge;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Represents a badge linked to a certain user, won at a certain time.
 */
@Entity
public final class BadgeCard implements Serializable {

    private static final long serialVersionUID = -6048896600632620497L;

    @Id
    @GeneratedValue
    @Column(name="BADGE_ID")
    private final Long badgeId;

    private final Long userId;
    private final Long badgeTimestamp;

    @Convert(converter = BadgeConverter.class)
    private final Badge badge;

    public BadgeCard(Long badgeId, Long userId, Long badgeTimestamp, Badge badge) {
        this.badgeId = badgeId;
        this.userId = userId;
        this.badgeTimestamp = badgeTimestamp;
        this.badge = badge;
    }

    public BadgeCard(final Long userId, final Badge badge){
        this(null, userId, System.currentTimeMillis(), badge);
    }

    public BadgeCard(){
        this(null, null, null, null);
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBadgeTimestamp() {
        return badgeTimestamp;
    }

    public Badge getBadge() {
        return badge;
    }

    @Override
    public String toString() {
        return "BadgeCard{" +
                "badgeId=" + badgeId +
                ", userId=" + userId +
                ", badgeTimestamp=" + badgeTimestamp +
                ", badge=" + badge +
                '}';
    }
}
