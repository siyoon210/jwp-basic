package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    boolean support(Object handler);

    ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, Object handler);
}
