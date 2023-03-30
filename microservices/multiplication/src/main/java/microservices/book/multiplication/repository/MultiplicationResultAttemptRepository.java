package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttempt, Long> {

    @Query(value = "SELECT * FROM MULTIPLICATION_ATTEMPT RIGHT JOIN (SELECT * FROM MULT_USER WHERE USER_ALIAS = :userAlias)", nativeQuery = true)
    List<MultiplicationResultAttempt> findFirst5ByUserAlias(String userAlias);
}