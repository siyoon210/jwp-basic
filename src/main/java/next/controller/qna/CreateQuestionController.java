package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static next.controller.UserSessionUtils.isLogined;

public class CreateQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isLogined(request.getSession())) {
            log.info("Login required.");
            return jspView("redirect:/users/loginForm");
        }

        Question question = new Question(request);
        QuestionDao questionDao = new QuestionDao();
        question = questionDao.insert(question);

        log.info("Create Question. {}", question.toString());
        return jspView("redirect:/");
    }
}
