package next.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.mvc.annotation.RequestUrl;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestUrl(url = "/")
public class HomeController extends AbstractController {
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}
