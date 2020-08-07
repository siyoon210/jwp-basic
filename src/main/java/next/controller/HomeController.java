package next.controller;

import core.mvc.Controller;
import core.mvc.modelandview.ModelAndView;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        return ModelAndView.builder()
                .addAttribute("questions", questionDao.findAll())
                .jspView("home.jsp");
    }
}
