package core.ref;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        final Method[] methods = clazz.getMethods();
        Arrays.stream(methods)
                .filter(m -> m.getName().startsWith("test"))
                .forEach(m -> {
                    try {
                        m.invoke(clazz.getDeclaredConstructor().newInstance());
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
    }
}
