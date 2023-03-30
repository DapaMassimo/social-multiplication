package microservices.book.multiplication.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public final class Multiplication implements Serializable {
    private static final long serialVersionUID = -7127973831836117855L;
    @Id
    @GeneratedValue
    @Column(name="MULTIPLICATION_ID")
    private Long id;

    @JsonProperty("factorA")
    @Column(name = "FACTOR_A")
    private final Integer factorA;

    @JsonProperty("factorB")
    @Column(name = "FACTOR_B")
    private final Integer factorB;

    public Multiplication(Integer factorA, Integer factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
    }

    public Multiplication(){
        this.factorA = null;
        this.factorB = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFactorA() {
        return factorA;
    }

    public Integer getFactorB() {
        return factorB;
    }

    @Override
    public String toString() {
        return "Multiplication{" +
                "id=" + id +
                ", factorA=" + factorA +
                ", factorB=" + factorB +
                '}';
    }
}