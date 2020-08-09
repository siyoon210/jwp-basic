package next.controller.qna;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {
    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long answerId = Long.parseLong(request.getParameter("answerId"));

        ModelAndView mav = jsonView();
        try {
            final Question question = decreaseCountOfComment(answerId);
            answerDao.delete(answerId);
            mav.addObject("result", Result.ok())
            .addObject("question", question);
        } catch (DataAccessException e) {
            mav.addObject("result", Result.fail(e.getMessage()));
        }
        return mav;
    }

    private Question decreaseCountOfComment(Long answerId) {
        final Answer answer = answerDao.findById(answerId);
        questionDao.decreaseCountOfAnswer(answer.getQuestionId());
        return questionDao.findById(answer.getQuestionId());
    }
}
