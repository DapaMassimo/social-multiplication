package microservices.book.multiplication.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "MULTIPLICATION_ATTEMPT")
public final class MultiplicationResultAttempt implements Serializable {
    private static final long serialVersionUID = -3255030241326223487L;
    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("user")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private final User user;

    @JsonProperty("multiplication")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MULTIPLICATION_ID")
    private final Multiplication multiplication;

    @JsonProperty("resultAttempt")
    private final int resultAttempt;

    @JsonProperty("correct")
    private final Boolean correct;

    public MultiplicationResultAttempt(User user, Multiplication multiplication, int resultAttempt, Boolean correct) {
        this.user = user;
        this.multiplication = multiplication;
        this.resultAttempt = resultAttempt;
        this.correct = correct;
    }

    public MultiplicationResultAttempt(){
        user = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }

    public User getUser() {
        return user;
    }

    public Multiplication getMultiplication() {
        return multiplication;
    }

    public int getResultAttempt() {
        return resultAttempt;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiplicationResultAttempt that = (MultiplicationResultAttempt) o;
        return resultAttempt == that.resultAttempt && Objects.equals(id, that.id) && Objects.equals(user, that.user) &&
                Objects.equals(multiplication, that.multiplication) && Objects.equals(correct, that.correct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, multiplication, resultAttempt, correct);
    }
}
