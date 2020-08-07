package core.mvc.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JspView implements View {
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final String url;

    public JspView(String url) {
        this.url = url;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws Exception {
        move(req, resp, model);
    }

    private void move(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model)
            throws ServletException, IOException {
        if (url.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(url.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        model.forEach(req::setAttribute);
        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }
}
