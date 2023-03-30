package microservices.book.gamification.service;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.domain.*;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import microservices.book.gamification.service.impl.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImplTest.class);


    private GameServiceImpl gameService;

    @Mock
    private BadgeCardRepository badgeCardRepository;

    @Mock
    private ScoreCardRepository scoreCardRepository;


    @BeforeAll
    void initAll(){
        MockitoAnnotations.openMocks(this);
        gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository);
    }

    @Test
    public void processFirstCorrectAttemptTest(){
        // given
        Long attemptId = 8L;
        Long userId = 1L;
        int totalScore = 10; // Default value set in ScoreCard constructor
        ScoreCard scoreCard = new ScoreCard(userId, attemptId, true);
        LeaderBoardRow leaderBoardRow = new LeaderBoardRow(userId, totalScore);
        BadgeCard badgeCard = new BadgeCard(userId, Badge.FIRST_ATTEMPT);

        given(scoreCardRepository.getTotalScoreForUser(userId)).willReturn(totalScore);
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId)).willReturn(Collections.singletonList(scoreCard));
        given(scoreCardRepository.findFirst10()).willReturn(Collections.singletonList(leaderBoardRow));

        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(Collections.singletonList(badgeCard));
        GameStats iteration = new GameStats();
        // when
        try {
            iteration = gameService.newAttemptForUser(userId, attemptId, true);
        } catch (MicroServiceException e){
            logger.debug("{}", e.getMessage());
        }

        // then
        assertThat(iteration.getScore()).isEqualTo(scoreCard.getScore());
        assertThat(iteration.getBadgeList()).containsOnly(badgeCard.getBadge());

    }
}
