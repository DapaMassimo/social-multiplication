package microservices.book.multiplication.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MULT_USER")
public final class User implements Serializable {

    private static final long serialVersionUID = 3822872428576794879L;
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @JsonProperty("alias")
    @Column(name = "USER_ALIAS")
    private final String alias;


    @JsonCreator
    public User(@JsonProperty("alias") String alias){
        this.alias = alias;
    }

    public User(){
        this.alias = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", alias='" + alias + '\'' +
                '}';
    }
}
