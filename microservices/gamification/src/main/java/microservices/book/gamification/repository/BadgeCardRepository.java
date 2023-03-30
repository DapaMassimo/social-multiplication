package microservices.book.gamification.repository;

import microservices.book.gamification.domain.BadgeCard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

    /**
     * Retrieves all the BadgeCards for a given user,
     * identified by his user id.
     * @param userId the id of the user
     * @return a list containing all the BadgeCards for the given user,
     * sorted by most recent.
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
