package core.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonView implements View {
    private final Object object;

    public JsonView(Object object) {
        this.object = object;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(object));
    }
}
