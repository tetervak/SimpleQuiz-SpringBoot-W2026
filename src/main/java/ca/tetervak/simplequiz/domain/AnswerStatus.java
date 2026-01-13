package ca.tetervak.simplequiz.domain;

public enum AnswerStatus {

    NOT_ANSWERED,
    RIGHT_ANSWER,
    WRONG_ANSWER,
    INVALID_INPUT;

    public static AnswerStatus getStatus(
            int correctAnswer,
            String userAnswer
    ){
        if(userAnswer == null || userAnswer.isEmpty()){
            return NOT_ANSWERED;
        }

        try {
            double entered = Double.parseDouble(userAnswer);
            if(Math.abs(entered - correctAnswer) <= TOLERANCE){
                return RIGHT_ANSWER;
            }
            else{
                return WRONG_ANSWER;
            }
        } catch(NumberFormatException e){
            return INVALID_INPUT;
        }
    }

    private final static double TOLERANCE = 0.00001;
}
