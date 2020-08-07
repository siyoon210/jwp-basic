package next.controller.qna;

import core.mvc.Controller;
import core.mvc.modelandview.ModelAndView;
import next.dao.AnswerDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();

        answerDao.delete(answerId);

        return ModelAndView.builder()
                .jsonView();
    }
}
