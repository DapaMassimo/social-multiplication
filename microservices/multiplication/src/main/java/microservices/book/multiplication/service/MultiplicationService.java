package microservices.book.multiplication.service;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {
    Multiplication createRandomMultiplication();

    Boolean checkAttempt(final MultiplicationResultAttempt multiplicationResultAttempt) throws MicroServiceException;

    List<MultiplicationResultAttempt> getStatsForUsers(String userAlias) throws MicroServiceException;

    MultiplicationResultAttempt getMultiplicationResultAttemptById(Long multiplicationResultAttemptId) throws MicroServiceException;
}
