package core.mvc;

import com.google.common.collect.Lists;
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
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private LegacyRequestMapping rm;
    private AnnotationHandlerMapping am;
    private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();

    @Override
    public void init() throws ServletException {
        rm = new LegacyRequestMapping();
        rm.initMapping();
        am = new AnnotationHandlerMapping();
        am.initMapping();

        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            final Object handler = getHandler(req);
            ModelAndView mav = execute(req, resp, handler);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.support(handler)) {
                return handlerAdapter.execute(req, resp, handler);
            }
        }
        throw new IllegalStateException();
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
