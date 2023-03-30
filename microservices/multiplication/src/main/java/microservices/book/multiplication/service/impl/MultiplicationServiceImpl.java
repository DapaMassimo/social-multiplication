package microservices.book.multiplication.service.impl;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.MultiplicationRepository;
import microservices.book.multiplication.repository.UserRepository;
import microservices.book.multiplication.service.MultiplicationService;
import microservices.book.multiplication.service.RandomGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private static final Logger logger = LoggerFactory.getLogger(MultiplicationServiceImpl.class);


    private final RandomGeneratorService randomGeneratorService;

    private final MultiplicationResultAttemptRepository attemptRepository;

    private final UserRepository userRepository;

    private final MultiplicationRepository multiplicationRepository;

    private final EventDispatcher eventDispatcher;

    @Autowired
    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService,
                                     MultiplicationResultAttemptRepository attemptRepository,
                                     UserRepository userRepository,
                                     MultiplicationRepository multiplicationRepository,
                                     EventDispatcher eventDispatcher) {
        this.randomGeneratorService = randomGeneratorService;
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
        this.multiplicationRepository = multiplicationRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        Integer factorA = this.randomGeneratorService.generateRandomFactor();
        Integer factorB = this.randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public Boolean checkAttempt(MultiplicationResultAttempt attempt) throws MicroServiceException{

        User user = userRepository.findByAlias(attempt.getUser().getAlias()).orElse(attempt.getUser());

        Multiplication multiplication = multiplicationRepository.findByFactorAAndFactorB(
                attempt.getMultiplication().getFactorA(), attempt.getMultiplication().getFactorB()).orElse(attempt.getMultiplication());

        boolean isCorrect = ((attempt.getMultiplication().getFactorA() * attempt.getMultiplication().getFactorB()) == attempt.getResultAttempt());
        logger.debug("{}, {}, isCorrect: {}", user, multiplication, isCorrect);
        Assert.isTrue(!attempt.getCorrect(), "You can't send an attempt marked as correct!");

        attempt = new MultiplicationResultAttempt(user, multiplication, attempt.getResultAttempt(), isCorrect);

        attemptRepository.save(attempt);

        // Communicates the result via Event
        eventDispatcher.send(new MultiplicationSolvedEvent(attempt.getUser().getId(),
                attempt.getId(), attempt.getCorrect()));

        return isCorrect;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUsers(String userAlias) {
        return attemptRepository.findFirst5ByUserAlias(userAlias);
    }

    @Override
    public MultiplicationResultAttempt getMultiplicationResultAttemptById(Long multiplicationResultAttemptId) throws MicroServiceException {
        MultiplicationResultAttempt multiplicationResultAttempt = attemptRepository.
                findById(multiplicationResultAttemptId).
                orElseThrow(() -> new MicroServiceException("Multiplication id not found in repository!!"));

        return multiplicationResultAttempt;
    }
}
