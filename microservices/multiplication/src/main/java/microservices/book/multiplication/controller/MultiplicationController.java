package microservices.book.multiplication.controller;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.multiplication.domain.GetProvaPayloadRequest;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.ProvaPayloadResponse;
import microservices.book.multiplication.service.impl.MultiplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController("MultiplicationController")
@RequestMapping("/multiplications")
final class MultiplicationController {

    private final MultiplicationServiceImpl multiplicationService;

    private static final Logger logger = LoggerFactory.getLogger(MultiplicationController.class);


    @Autowired
    public MultiplicationController(MultiplicationServiceImpl multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @GetMapping("/random")
    Multiplication getRandomMultiplication() throws MicroServiceException {
        return multiplicationService.createRandomMultiplication();
    }

    @GetMapping("/getFactors")
    List<Integer> getMultiplicationFactor(Long multiplicationResultAttemptId) throws MicroServiceException{
        MultiplicationResultAttempt multiplicationResultAttempt = multiplicationService.
                getMultiplicationResultAttemptById(multiplicationResultAttemptId);

        return new ArrayList<>(Arrays.asList(multiplicationResultAttempt.getMultiplication().getFactorA(),
                multiplicationResultAttempt.getMultiplication().getFactorB()));
    }

}
