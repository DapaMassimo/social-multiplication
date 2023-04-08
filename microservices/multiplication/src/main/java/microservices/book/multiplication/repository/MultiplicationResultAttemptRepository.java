package microservices.book.multiplication.repository;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttempt, Long> {

    @Query(value = "SELECT * FROM MULTIPLICATION_ATTEMPT AS ma WHERE ma.USER_ID = (SELECT USER_ID FROM MULT_USER WHERE USER_ALIAS = :userAlias LIMIT 1) ORDER BY ID DESC LIMIT 5", nativeQuery = true)
    List<MultiplicationResultAttempt> findFirst5ByUserAlias(String userAlias);
}