package microservices.book.gamification.client;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This implementation of MultiplicationResultAttemptClient
 * interface connects to the Multiplication microservice via REST.
 */
@Component
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient{

    private static final Logger logger = LoggerFactory.getLogger(MultiplicationResultAttemptClientImpl.class);

    private final RestTemplate restTemplate;

    private final String multiplicationHost;

    @Autowired
    public MultiplicationResultAttemptClientImpl(final RestTemplate restTemplate,
                                                 @Value("${multiplication.host}") final String multiplicationHost) {
        this.restTemplate = restTemplate;
        this.multiplicationHost = multiplicationHost;
    }

    @Override
    public MultiplicationResultAttempt retrieveMultiplicationResultAttemptByAttemptId(Long attemptId) {
        String uri = this.multiplicationHost + "/results" + "/" + attemptId;
        return restTemplate.getForObject(
                uri,
                MultiplicationResultAttempt.class
        );
    }
}
