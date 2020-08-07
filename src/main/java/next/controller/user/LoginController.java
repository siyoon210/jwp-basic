package next.controller.user;

import core.mvc.Controller;
import core.mvc.modelandview.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            return ModelAndView.builder()
                    .addAttribute("loginFailed", true)
                    .jspView("/user/login.js");
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return ModelAndView.builder()
                    .jspView("redirect:/");
        } else {
            return ModelAndView.builder()
                    .addAttribute("loginFailed", true)
                    .jspView("/user/login.jsp");
        }
    }
}
