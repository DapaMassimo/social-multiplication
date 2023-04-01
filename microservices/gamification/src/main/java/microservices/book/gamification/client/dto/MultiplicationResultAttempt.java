package microservices.book.gamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import microservices.book.gamification.client.MultiplicationResultAttemptDeserializer;

@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
public final class MultiplicationResultAttempt {

    private final String userAlias;
    private final Integer factorA;

    private final Integer factorB;

    private final Boolean correct;

    private final Integer resultAttempt;


    public MultiplicationResultAttempt() {
        this.userAlias = null;
        this.factorA = null;
        this.factorB = null;
        this.correct = null;
        this.resultAttempt = null;
    }

    public MultiplicationResultAttempt(String userAlias, Integer factorA, Integer factorB, Integer resultAttempt, Boolean correct) {
        this.userAlias = userAlias;
        this.factorA = factorA;
        this.factorB = factorB;
        this.correct = correct;
        this.resultAttempt = resultAttempt;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public Integer getFactorA() {
        return factorA;
    }

    public Integer getFactorB() {
        return factorB;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public Integer getResultAttempt() {
        return resultAttempt;
    }
}
