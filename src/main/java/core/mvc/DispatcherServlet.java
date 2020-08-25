package core.mvc;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private LegacyRequestMapping rm;
    private AnnotationHandlerMapping am;

    @Override
    public void init() throws ServletException {
        rm = new LegacyRequestMapping();
        rm.initMapping();
        am = new AnnotationHandlerMapping();
        am.initMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        ModelAndView mav;
        try {
            final Object handler = getHandler(req);

            if (handler instanceof LegacyRequestMapping) {
                mav = ((Controller) handler).execute(req, resp);
            } else if (handler instanceof AnnotationHandlerMapping) {
                mav = ((AnnotationHandlerMapping) handler).getHandler(req).handle(req, resp);
            } else {
                throw new IllegalArgumentException();
            }

            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object getHandler(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();

        final Controller controller = rm.findController(requestURI);
        if (controller != null) {
            return controller;
        }

        final HandlerExecution handler = am.getHandler(request);
        if (handler != null) {
            return handler;
        }

        throw new IllegalArgumentException();
    }
}
