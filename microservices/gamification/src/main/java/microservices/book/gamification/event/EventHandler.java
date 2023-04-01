package microservices.book.gamification.event;

import common.service.domain.base.exception.MicroServiceException;
import microservices.book.gamification.service.impl.GameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * This class receives the events and triggers the
 * associated business logic.
 */


@Component
public class EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventHandler.class);


    private final GameServiceImpl gameService;

    EventHandler(final GameServiceImpl gameService){
        this.gameService = gameService;
    }

    @RabbitListener(queues = "${multiplication.queue}")
    void handleMultiplicationSolved(final MultiplicationSolvedEvent multiplicationEvent) throws AmqpRejectAndDontRequeueException{
        logger.debug("MultiplicationSolvedEvent: {}", multiplicationEvent);
        try {
            gameService.newAttemptForUser(multiplicationEvent.getUserId(),
                    multiplicationEvent.getMultiplicationResultAttemptId(),
                    multiplicationEvent.getCorrect());
        } catch (MicroServiceException mse){
            logger.error("Error when trying to process MultiplicationSolvedEvent", mse);
            // Avoids the event to be re-queued and reprocessed
            throw new AmqpRejectAndDontRequeueException(mse);
        }

    }

}