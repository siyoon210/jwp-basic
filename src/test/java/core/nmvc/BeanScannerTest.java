package core.nmvc;

import core.di.factory.BeanScanner;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BeanScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(BeanScannerTest.class);

    private BeanScanner cf;

    @Before
    public void setup() {
        cf = new BeanScanner("core.nmvc");
    }

    @Test
    public void getBeans() throws Exception {
        Map<Class<?>, Object> beans = cf.getBeans();
        for (Class<?> bean : beans.keySet()) {
            logger.debug("bean : {}", bean);
        }
    }
}
