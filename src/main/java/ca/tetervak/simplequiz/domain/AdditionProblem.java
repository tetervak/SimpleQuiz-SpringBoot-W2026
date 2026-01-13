package ca.tetervak.simplequiz.domain;

import java.io.Serializable;

public record AdditionProblem(
        int firstOperand,
        int secondOperand,
        int correctAnswer
) implements Serializable {
}
