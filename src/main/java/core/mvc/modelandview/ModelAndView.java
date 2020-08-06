package core.mvc.modelandview;

import core.mvc.view.View;

import java.util.Map;

public class ModelAndView {
    private final Map<String, Object> model;
    private final View view;

    public ModelAndView(Map<String, Object> model, View view) {
        this.model = model;
        this.view = view;
    }
}
