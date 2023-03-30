package microservices.book.gamification.service.impl;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import microservices.book.gamification.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private final ScoreCardRepository scoreCardRepository;

    private final BadgeCardRepository badgeCardRepository;

    @Autowired
    public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository) {
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
    }

    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) throws MicroServiceException {
        ScoreCard scoreCard = new ScoreCard(userId, attemptId, correct);
        scoreCardRepository.save(scoreCard);
        processForBadges(userId, attemptId, scoreCard.isCorrect());
        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        GameStats gameStats = new GameStats(userId, totalScore,
                badgeCardList.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));

        return gameStats;
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) throws MicroServiceException{
        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        logger.debug("{}", badgeCardList);
        Integer totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        logger.debug("totalScore: {}", totalScore);
        totalScore = totalScore == null ? 0 : totalScore;
        GameStats gameStats = new GameStats(userId, totalScore,
            badgeCardList.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
        return gameStats;
    }



    /**
     * Checks the total score and the different {@link ScoreCard}s obtained
     * to give new badges in case their conditions are met
     *
     * @param userId
     * @param attemptId
     * @return The list of badges won (if any) with the provided attemptId
     */
    private void processForBadges(Long userId, Long attemptId, boolean correct) throws MicroServiceException {
        List<ScoreCard> usc = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
        List<BadgeCard> ubc = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        int userTotalScore = scoreCardRepository.getTotalScoreForUser(userId);

        if(usc != null) {
            if (usc.size() == 1 && (ubc == null ||
                    !ubc.parallelStream().anyMatch(badgeCard -> badgeCard.getBadge().equals(Badge.FIRST_ATTEMPT)))) {
                // first attempt
                badgeCardRepository.save(new BadgeCard(userId, Badge.FIRST_ATTEMPT));
                logger.debug("Badge {} given to userId {}", Badge.FIRST_ATTEMPT.name(), userId);
            }
            // we save scoreCards both for correct and incorrect attempts
            if (correct) {
                if(usc.parallelStream().noneMatch(scoreCard -> (scoreCard.isCorrect()
                        && scoreCard.getAttemptId() != attemptId)) &&
                        (ubc == null || ubc.parallelStream().noneMatch(badgeCard -> badgeCard.getBadge().equals(Badge.FIRST_WON)))) {
                    // first correct attempt
                    badgeCardRepository.save(new BadgeCard(userId, Badge.FIRST_WON));
                    logger.debug("Badge {} given to userId {}", Badge.FIRST_WON.name(), userId);
                }
            }

        } else {
            logger.error("User has no scoreCards in repository!");
            throw new MicroServiceException("User scoreCard list is emtpy!");
        }

        if(ubc != null){
            checkAndGiveBadgesBasedOnScore(userId, userTotalScore, ubc, badgeCardRepository, Badge.BRONZE_MULTIPLICATOR);
            checkAndGiveBadgesBasedOnScore(userId, userTotalScore, ubc, badgeCardRepository, Badge.SILVER_MULTIPLICATOR);
            checkAndGiveBadgesBasedOnScore(userId, userTotalScore, ubc, badgeCardRepository, Badge.GOLD_MULTIPLICATOR);
        } else {
            logger.error("User has no badgeCards in repository!");
            throw new MicroServiceException("User badgeCard list is emtpy!");
        }
    }

    private void checkAndGiveBadgesBasedOnScore(Long userId, int userTotalScore, List<BadgeCard> badgeCardList,
                                                BadgeCardRepository badgeCardRepository, Badge badge) throws MicroServiceException{
        if(userTotalScore == badge.getPoints() &&
                !badgeCardList.parallelStream().anyMatch(badgeCard -> badgeCard.getBadge().equals(badge))){
            BadgeCard badgeCardWon = new BadgeCard(userId, badge);
            badgeCardList.add(badgeCardWon);
            badgeCardRepository.save(badgeCardWon);
            logger.debug("Badge {} given to userId {}", badge.name(), userId);
        }
    }

}
