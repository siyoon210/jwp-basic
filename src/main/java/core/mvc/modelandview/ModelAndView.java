package core.mvc.modelandview;

import core.mvc.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private final Map<String, Object> model;
    private final View view;

    public ModelAndView(View view) {
        this.model = new HashMap<>();
        if (view == null) {
            throw new IllegalArgumentException("View should be not null.");
        }
        this.view = view;
    }

    public ModelAndView addAttribute(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        view.render(req, resp, model);
    }
}
