package core.nmvc;

import core.di.factory.BeanScanner;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class BeanScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(BeanScannerTest.class);

    private BeanScanner cf;

    @Before
    public void setup() {
        cf = new BeanScanner("core.nmvc");
    }

    @Test
    public void getBeans() throws Exception {
        final Set<Class<?>> scan = cf.scan();
        for (Class<?> aClass : scan) {
            logger.debug("bean : {}", aClass);
        }
    }
}
