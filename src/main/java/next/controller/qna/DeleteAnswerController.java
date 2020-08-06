package next.controller.qna;

import core.mvc.Controller;
import core.mvc.modelandview.ModelAndView;
import core.mvc.view.JsonView;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();

        answerDao.delete(answerId);

        return new ModelAndView(null, new JsonView(Result.ok()));

    }
}
