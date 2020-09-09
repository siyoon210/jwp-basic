package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import next.controller.user.CreateUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @RequestMapping("/users/findUserId")
    public ModelAndView findUserId(HttpServletRequest request, HttpServletResponse response) {
        log.debug("findUserID");
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        log.debug("s a v e");
        return null;
    }
}
