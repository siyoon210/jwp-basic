package next.controller;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.mvc.annotation.RequestUrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestUrl(url = "/users/form")
public class UserFormForwardController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String forwardUrl = "/user/form.jsp";
        return jspView(forwardUrl);
    }

}
