package microservices.book.gamification.service.impl;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.client.MultiplicationResultAttemptClient;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import microservices.book.gamification.domain.*;
import microservices.book.gamification.domain.badge.Badge;
import microservices.book.gamification.domain.badge.ScoreBadge;
import microservices.book.gamification.domain.badge.SpecialBadge;
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

    private final MultiplicationResultAttemptClient multiplicationResultAttemptClient;

    @Autowired
    public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository,
                           MultiplicationResultAttemptClient multiplicationResultAttemptClient) {
        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
        this.multiplicationResultAttemptClient = multiplicationResultAttemptClient;
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
        Integer totalScore = scoreCardRepository.getTotalScoreForUser(userId);
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

        logger.debug(usc.toString());
        logger.debug(ubc.toString());

        if(usc != null) {
            if (usc.size() == 1 && !badgeAlreadyAchieved(ubc, SpecialBadge.FIRST_ATTEMPT)) {
                // first attempt
                badgeCardRepository.save(new BadgeCard(userId, SpecialBadge.FIRST_ATTEMPT));
            }
            // we save scoreCards both for correct and incorrect attempts
            if (correct) {
                if(usc.stream().noneMatch(scoreCard -> (scoreCard.isCorrect() && scoreCard.getAttemptId().intValue() != attemptId))) {
                    if (!badgeAlreadyAchieved(ubc, SpecialBadge.FIRST_WON)) {
                        // first correct attempt
                        badgeCardRepository.save(new BadgeCard(userId, SpecialBadge.FIRST_WON));
                    }
                }

                if(!badgeAlreadyAchieved(ubc, SpecialBadge.LUCKY_NUMBER)){
                    MultiplicationResultAttempt multiplicationResultAttempt = multiplicationResultAttemptClient.retrieveMultiplicationResultAttemptByAttemptId(attemptId);
                    if(multiplicationResultAttempt.getFactorA() == 42 || multiplicationResultAttempt.getFactorB() == 42) {
                        badgeCardRepository.save(new BadgeCard(userId, SpecialBadge.LUCKY_NUMBER));
                    }
                }
            }

        } else {
            logger.error("User has no scoreCards in repository!");
            throw new MicroServiceException("User scoreCard list is emtpy!");
        }

        if(ubc != null){
            checkAndGiveBadgesBasedOnScore(userId, userTotalScore, ubc, badgeCardRepository, ScoreBadge.BRONZE_MULTIPLICATOR);
            checkAndGiveBadgesBasedOnScore(userId, userTotalScore, ubc, badgeCardRepository, ScoreBadge.SILVER_MULTIPLICATOR);
            checkAndGiveBadgesBasedOnScore(userId, userTotalScore, ubc, badgeCardRepository, ScoreBadge.GOLD_MULTIPLICATOR);
        } else {
            logger.debug("User has no badgeCards in repository!");
        }
    }

    private void checkAndGiveBadgesBasedOnScore(Long userId, int userTotalScore, List<BadgeCard> badgeCardList,
                                                BadgeCardRepository badgeCardRepository, Badge badge) throws MicroServiceException{
        if(userTotalScore == badge.getPoints() &&
                badgeCardList.parallelStream().noneMatch(badgeCard -> badgeCard.getBadge().equals(badge))){
            BadgeCard badgeCardWon = new BadgeCard(userId, badge);
            badgeCardList.add(badgeCardWon);
            badgeCardRepository.save(badgeCardWon);
        }
    }

    private boolean badgeAlreadyAchieved(List<BadgeCard> badgeCardList, Badge badge){
        if(badgeCardList != null && !badgeCardList.isEmpty() && badgeCardList.stream().anyMatch(badgeCard ->
            badgeCard.getBadge().toString().equalsIgnoreCase(badge.toString())))
            return true;
        return false;
    }

}
