package microservices.book.multiplication.service;


import static org.assertj.core.api.Assertions.assertThat;

import microservices.book.multiplication.service.impl.RandomGeneratorServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RandomGeneratorServiceImplTest {

    private RandomGeneratorServiceImpl randomGeneratorServiceImp;

    @BeforeAll
    public void initAll(){
        randomGeneratorServiceImp = new RandomGeneratorServiceImpl();
    }

    @Test
    @DisplayName("Random numbers are in range")
    public void generateRandomFactorIsBetweenExpectedLimits() {
        List<Integer> randomFactors = IntStream.range(0,1000)
                .map(i -> randomGeneratorServiceImp.generateRandomFactor())
                .boxed()
                .collect(Collectors.toList());

        for(Integer i: randomFactors){
            assertThat(i).isIn(
                    IntStream.range(11, 100).
                            boxed().collect(Collectors.toList()));
        }
    }
}
