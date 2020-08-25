package core.nmvc;

import core.mvc.Controller;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private final Map<Class<Controller>, Object> controllers = new HashMap<>();

    public ControllerScanner() {
        final Reflections reflections = new Reflections("");
        final Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(core.annotation.Controller.class);

        annotated.forEach(a -> {
            try {
                controllers.put((Class<Controller>) a, a.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    public Map<Class<Controller>, Object> getControllers() {
        return controllers;
    }
}
