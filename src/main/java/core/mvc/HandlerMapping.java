package core.mvc;

import core.annotation.RequestMethod;

public interface HandlerMapping {
    void initMapping();

    Controller findController(String url, RequestMethod requestMethod);
}
