package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class QuestionListApiController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionListApiController.class);
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final List<Question> questions = questionDao.findAll();
        log.info("Question List Api request. Questions {}", questions);
        return jsonView().addObject("questions", questions);
    }
}
