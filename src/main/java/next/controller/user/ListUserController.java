package next.controller.user;

import core.mvc.Controller;
import core.mvc.modelandview.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return ModelAndView.builder()
                    .jspView("redirect:/users/loginForm");
        }

        UserDao userDao = new UserDao();

        return ModelAndView.builder()
                .addAttribute("users", userDao.findAll())
                .jspView("/user/list.jsp");
    }
}
