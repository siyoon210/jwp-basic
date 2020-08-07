package core.mvc.modelandview;

import core.mvc.view.JsonView;
import core.mvc.view.JspView;
import core.mvc.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private final Map<String, Object> model;
    private final View view;

    private ModelAndView(Map<String, Object> model, View view) {
        this.model = model;
        this.view = view;
    }

    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        view.render(req, resp, model);
    }

    public static ModelAndViewBuilder builder() {
        return new ModelAndViewBuilder();
    }

    public static class ModelAndViewBuilder {
        private final Map<String, Object> model;
        private View view;

        public ModelAndViewBuilder() {
            this.model = new HashMap<>();
        }

        public ModelAndViewBuilder addAttribute(String key, Object value) {
            model.put(key, value);
            return this;
        }

        public ModelAndView jspView(String viewName) {
            view = new JspView(viewName);
            return new ModelAndView(model, view);
        }

        public ModelAndView jsonView() {
            view = new JsonView();
            return new ModelAndView(model, view);
        }
    }
}
