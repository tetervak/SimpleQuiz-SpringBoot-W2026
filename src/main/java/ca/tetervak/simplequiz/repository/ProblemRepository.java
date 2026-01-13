package ca.tetervak.simplequiz.repository;

import ca.tetervak.simplequiz.domain.AdditionProblem;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class ProblemRepository {

    private final Random random = new Random();

    public AdditionProblem getRandomAdditionProblem(){
        int a = 1 + random.nextInt(10);
        int b = 1 + random.nextInt(10);
        int answer = a + b;

        return new AdditionProblem(a, b, answer);
    }
}

