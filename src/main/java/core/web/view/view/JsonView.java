package core.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

public class JsonView implements View {
    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(model));
    }

    private Map<String, Object> createModel(HttpServletRequest req, Map<String, Object> model) {
        final Enumeration<String> names = req.getAttributeNames();
        while (names.hasMoreElements()) {
            final String name = names.nextElement();
            model.put(name, req.getAttribute(name));
        }

        return model;
    }
}
