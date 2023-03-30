package microservices.book.multiplication.event;

import java.io.Serializable;
import java.util.Objects;

public class MultiplicationSolvedEvent implements Serializable {
    private static final Long serialVersionUID = 1L;

    private final Long userId;
    private final Long multiplicationResultAttemptId;
    private final Boolean correct;

    public MultiplicationSolvedEvent(Long userId, Long multiplicationResultAttemptId, Boolean correct) {
        this.userId = userId;
        this.multiplicationResultAttemptId = multiplicationResultAttemptId;
        this.correct = correct;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMultiplicationResultAttemptId() {
        return multiplicationResultAttemptId;
    }

    public Boolean getCorrect() {
        return correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiplicationSolvedEvent that = (MultiplicationSolvedEvent) o;
        return Objects.equals(userId, that.userId) && Objects.equals(multiplicationResultAttemptId,
                that.multiplicationResultAttemptId) && Objects.equals(correct, that.correct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, multiplicationResultAttemptId, correct);
    }
}
