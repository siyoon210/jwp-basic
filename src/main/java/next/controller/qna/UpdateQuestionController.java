package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static next.controller.UserSessionUtils.getUserFromSession;
import static next.controller.UserSessionUtils.isLogined;

public class UpdateQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UpdateQuestionController.class);

    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession();
        final User user = getUserFromSession(session);
        if (!isLogined(session) || Objects.isNull(user)) {
            log.info("Login required.");
            return jspView("redirect:/users/loginForm");
        }

        final long questionId = Long.parseLong(request.getParameter("questionId"));
        final Question question = questionDao.findById(questionId);

        //todo id로 검사하기
        if (!question.getWriter().equals(user.getName())) {
            log.info("Writer does not match.");
            return jspView("redirect:/");
        }

        question.update(request);
        questionDao.update(question);

        log.info("Update Question. {}", question.toString());
        return jspView("redirect:/");
    }
}
