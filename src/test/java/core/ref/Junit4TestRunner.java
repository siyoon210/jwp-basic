package core.ref;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        final Method[] methods = clazz.getMethods();
        Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(MyTest.class))
                .forEach(m -> {
                    try {
                        m.invoke(clazz.getDeclaredConstructor().newInstance());
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
    }
}
