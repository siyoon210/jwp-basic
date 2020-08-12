package core.mvc;

import core.mvc.annotation.RequestUrl;
import next.controller.qna.*;
import next.controller.user.*;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private final Map<String, Controller> mappings = new HashMap<>();

    public RequestMapping() {

    }

    void initMapping() {
        Reflections reflections = new Reflections("next.controller");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RequestUrl.class);

        for (Class<?> controller : annotated) {
            RequestUrl request = controller.getAnnotation(RequestUrl.class);
            try {
                put(request.url(), (Controller) controller.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

//        mappings.put("/", new HomeController());
//        mappings.put("/users/form", new FormForwardController("/user/form.jsp"));
//        mappings.put("/users/loginForm", new FormForwardController("/user/login.jsp"));
        mappings.put("/users", new ListUserController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());
//        mappings.put("/qna/form", new CreateQuestionFormController());
        mappings.put("/qna/create", new CreateQuestionController());
//        mappings.put("/qna/show", new ShowController());
        mappings.put("/qna/update-form", new UpdateFormQuestionController());
        mappings.put("/qna/update", new UpdateQuestionController());
        mappings.put("/qna/delete", new DeleteQuestionController());
        mappings.put("/api/qna/addAnswer", new AddAnswerController());
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());
        mappings.put("/api/qna/list", new QuestionListApiController());
        mappings.put("/api/qna/delete", new DeleteQuestionApiController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
