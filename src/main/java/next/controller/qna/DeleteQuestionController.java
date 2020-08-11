package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.User;
import next.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static next.controller.UserSessionUtils.isLogined;

public class DeleteQuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);
    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionService questionService = new QuestionService(questionDao, answerDao);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!isLogined(request.getSession())) {
            log.info("Login required.");
            return jspView("redirect:/users/loginForm");
        }

        final long questionId = Long.parseLong(request.getParameter("questionId"));
        final User userFromSession = UserSessionUtils.getUserFromSession(request.getSession());
        questionService.deleteQuestion(questionId, userFromSession);
        log.info("Delete Question. {}", questionId);
        return jspView("redirect:/");
    }
}
