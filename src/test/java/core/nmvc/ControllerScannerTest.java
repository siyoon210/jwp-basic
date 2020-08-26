package core.nmvc;

import core.mvc.Controller;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ControllerScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    private ControllerScanner cs;

    @Before
    public void init() {
        cs = new ControllerScanner();
    }

    @Test
    public void csTest() {
        final Map<Class<Controller>, Object> controllers = cs.getControllers();
        for (Class<Controller> controllerClass : controllers.keySet()) {
            logger.debug("Controller : {}", controllerClass.getName());
        }
    }
}
