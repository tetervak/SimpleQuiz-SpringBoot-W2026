package ca.tetervak.simplequiz.controller;

import ca.tetervak.simplequiz.domain.AdditionProblem;
import ca.tetervak.simplequiz.domain.AnswerStatus;
import ca.tetervak.simplequiz.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class QuizController {

    private final ProblemRepository repository;

    public QuizController(ProblemRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value={"/", "new-problem"})
    public ModelAndView newProblem(HttpSession session){
        log.trace("newProblem() is called");
        AdditionProblem problem = repository.getRandomAdditionProblem();
        log.debug("problem = " + problem);
        session.setAttribute("problem", problem);
        return new ModelAndView("new-problem", "problem", problem);
    }

    @RequestMapping( "see-answer")
    public ModelAndView seeAnswer(HttpSession session){
        log.trace("seeAnswer() is called");
        AdditionProblem problem = (AdditionProblem) session.getAttribute("problem");
        if (problem == null) {
            log.debug("The session data is not found.");
            return new ModelAndView("session-expired");
        } else {
            log.debug("problem = " + problem);
            return new ModelAndView("see-answer", "problem", problem);
        }
    }

    @RequestMapping("try-again")
    public ModelAndView tryAgain(HttpSession session){
        log.trace("tryAgain() is called");
        AdditionProblem problem = (AdditionProblem) session.getAttribute("problem");
        if (problem == null) {
            log.debug("The session data is not found.");
            return new ModelAndView("session-expired");
        } else {
            log.debug("problem = " + problem);
            return new ModelAndView("try-again", "problem", problem);
        }
    }

    @RequestMapping("check-answer")
    public String checkAnswer(
            @RequestParam String userAnswer,
            HttpSession session,
            Model model){
        log.trace("checkAnswer() is called");
        log.debug("userAnswer = " + userAnswer);
        AdditionProblem problem = (AdditionProblem) session.getAttribute("problem");
        if (problem == null) {
            log.debug("The session data is not found.");
            return "session-expired";
        } else {
            log.debug("problem = " + problem);
            model.addAttribute("userAnswer", userAnswer);
            model.addAttribute("problem", problem);
            return switch (AnswerStatus.getStatus(problem.correctAnswer(), userAnswer)) {
                case AnswerStatus.RIGHT_ANSWER -> "right-answer";
                case AnswerStatus.WRONG_ANSWER -> "wrong-answer";
                case AnswerStatus.NOT_ANSWERED, AnswerStatus.INVALID_INPUT -> "bad-input";
            };
        }
    }
}