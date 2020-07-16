package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/update/*")
public class UpdateUserFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(UpdateUserFormServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String userId = req.getPathInfo().substring(1);
        log.info("User ID : {}", userId);
        final User user = DataBase.findUserById(userId);
        log.info("User : {}", user.toString());
        req.setAttribute("user", user);
        final RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/update.jsp");
        requestDispatcher.forward(req, resp);
    }
}
