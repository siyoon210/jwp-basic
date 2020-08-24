package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;

        logger.debug("Print all public FIELDS");
        final Field[] fields = clazz.getFields();
        Arrays.stream(fields).forEach(f -> logger.debug(f.getName()));

        logger.debug("Print all public CONSTRUCTORS");
        final Constructor<?>[] constructors = clazz.getConstructors();
        Arrays.stream(constructors).forEach(c -> logger.debug(c.getName()));

        logger.debug("Print all public METHODS");
        final Method[] methods = clazz.getMethods();
        Arrays.stream(methods).forEach(m -> logger.debug(m.getName()));

        logger.debug("Print all non-public FIELDS");
        final Field[] declaredFields = clazz.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(f -> logger.debug(f.getName()));

        logger.debug("Print all non-public CONSTRUCTORS");
        final Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Arrays.stream(declaredConstructors).forEach(f -> logger.debug(f.getName()));

        logger.debug("Print all non-public METHODS");
        final Method[] declaredMethods = clazz.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(m -> logger.debug(m.getName()));
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
    }
    
    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
    }
}
