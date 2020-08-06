package core.mvc.modelandview;

import core.mvc.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ModelAndView {
    private final Map<String, Object> model;
    private final View view;

    public ModelAndView(Map<String, Object> model, View view) {
        this.model = model;
        this.view = view;
    }

    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        view.render(req, resp, model);
    }
}
