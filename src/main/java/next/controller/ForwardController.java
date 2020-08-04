package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ForwardController implements Controller {
    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
        if (Objects.isNull(forwardUrl)) {
            throw new NullPointerException("forwardUrl is null.");
        }
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        return forwardUrl;
    }
}
