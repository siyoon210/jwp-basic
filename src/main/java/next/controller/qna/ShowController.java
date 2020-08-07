package next.controller.qna;

import core.mvc.Controller;
import core.mvc.modelandview.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        return ModelAndView.builder()
                .addAttribute("question", questionDao.findById(questionId))
                .addAttribute("answers", answerDao.findAllByQuestionId(questionId))
                .jspView("/qna/show.jsp");
    }
}
