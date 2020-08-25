package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.Controller;
import core.mvc.HandlerMapping;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initMapping() {
        ControllerScanner controllerScanner = new ControllerScanner();
        final Map<Class<Controller>, Object> controllers = controllerScanner.getControllers();
        for (Map.Entry<Class<Controller>, Object> classObjectEntry : controllers.entrySet()) {
            final Set<Method> allMethods = ReflectionUtils.getAllMethods(classObjectEntry.getKey(), ReflectionUtils.withAnnotation(RequestMapping.class));

            for (Method method : allMethods) {
                final RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                final HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMapping.method());
                //todo HandlerExecution 만들기
                handlerExecutions.put(handlerKey, new HandlerExecution());
            }

        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
