package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static next.controller.UserSessionUtils.getUserFromSession;
import static next.controller.UserSessionUtils.isLogined;

public class CreateQuestionFormController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateQuestionFormController.class);

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession();
        final User user = getUserFromSession(session);
        if (!isLogined(session) || Objects.isNull(user)) {
            log.info("Login required.");
            return jspView("redirect:/users/loginForm");
        }

        return jspView("/qna/form.jsp").addObject("user", user);
    }
}
