package core.mvc;

import core.nmvc.HandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecutionHandlerAdapter implements HandlerAdapter{
    public boolean support(Object handler) {
        return handler instanceof HandlerExecution;
    }

    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        try {
            return ((Controller) handler).execute(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
