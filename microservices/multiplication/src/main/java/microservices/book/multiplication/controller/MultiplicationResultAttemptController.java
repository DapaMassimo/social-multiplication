package microservices.book.multiplication.controller;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.service.MultiplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("MultiplicationResultAttemptController")
@RequestMapping("/results")
final class MultiplicationResultAttemptController {

    private static final Logger logger = LoggerFactory.getLogger(MultiplicationResultAttemptController.class);

    private final MultiplicationService multiplicationService;

    private final MultiplicationResultAttemptRepository attemptRepository;

    @Autowired
    public MultiplicationResultAttemptController(MultiplicationService multiplicationService,
                                                 MultiplicationResultAttemptRepository attemptRepository) {
        this.multiplicationService = multiplicationService;
        this.attemptRepository = attemptRepository;
    }

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) throws MicroServiceException{
        Boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt);
        return ResponseEntity.ok(new
                MultiplicationResultAttempt(multiplicationResultAttempt.getUser(),
                        multiplicationResultAttempt.getMultiplication(),
                        multiplicationResultAttempt.getResultAttempt(),
                        isCorrect));
    }

    @GetMapping
    ResponseEntity<List<MultiplicationResultAttempt>> getStatsForUsers(@RequestParam("alias") String alias) throws MicroServiceException {
        return ResponseEntity.ok(multiplicationService.getStatsForUsers(alias));
    }

    @GetMapping("/{attemptId}")
    ResponseEntity<MultiplicationResultAttempt> getMultiplicationResultAttemptById(@PathVariable(name="attemptId") Long multiplicationResultAttemptId) throws MicroServiceException{
        return ResponseEntity.ok(multiplicationService.getMultiplicationResultAttemptById(multiplicationResultAttemptId));
    }
}
