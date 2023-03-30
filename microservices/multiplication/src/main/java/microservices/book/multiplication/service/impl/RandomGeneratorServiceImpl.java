package microservices.book.multiplication.service.impl;

import microservices.book.multiplication.service.RandomGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomGeneratorServiceImpl implements RandomGeneratorService {

    private static final Integer MAX_RANDOM = 99;
    private static final Integer MIN_RANDOM = 11;

    @Override
    public Integer generateRandomFactor() {
        return(new Random().nextInt((MAX_RANDOM - MIN_RANDOM)+1) + MIN_RANDOM);
    }
}
