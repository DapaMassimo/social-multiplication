package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import microservices.book.multiplication.service.impl.MultiplicationServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private MultiplicationRepository multiplicationRepository;

    @Mock
    private EventDispatcher eventDispatcher;

    @BeforeAll
    void initAll(){
        // tell mockito to process annotations
        MockitoAnnotations.openMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService,
                attemptRepository, userRepository, multiplicationRepository, eventDispatcher);
    }

    @Test
    void createRandomMultiplicationTest(){
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 12);

        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        assertThat(multiplication.getFactorA().equals(50));
        assertThat(multiplication.getFactorB().equals(12));
        assertThat(multiplication.getFactorB()*multiplication.getFactorA()).isEqualTo(600);
    }

    @Test
    void checkCorrectAttemptTest() throws Exception{
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(),
                attempt.getId(), true);

        // given
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        // when
        Boolean attemptCheck = multiplicationServiceImpl.checkAttempt(attempt);

        // then
        assertThat(attemptCheck).isTrue();
        verify(attemptRepository).save(eq(verifiedAttempt));
        verify(eventDispatcher).send(eq(event));

    }

    @Test
    void checkWrongAttemptTest() throws Exception{
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 5263, false);

        // given
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        // when
        Boolean attemptCheck = multiplicationServiceImpl.checkAttempt(attempt);

        // then
        assertThat(attemptCheck).isFalse();
        verify(attemptRepository).save(eq(attempt));
    }

    @Test
    public void retrieveStatsTest(){
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 202342,false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3234,false);
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);

        // given
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());
        given(attemptRepository.findFirst5ByUserAlias("john_doe")).willReturn(latestAttempts);

        // when
        List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUsers("john_doe");

        // then
        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);

    }
}
