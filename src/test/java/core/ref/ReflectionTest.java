package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
        final Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Arrays.stream(declaredConstructors)
                .forEach(c -> {
                    try {
                        final User u = (User) c.newInstance("siyoon", "1234", "puru", "puru@naver.com");
                        logger.debug("Created User. {}", u.toString());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        logger.debug(clazz.getName());
    }
    
    @Test
    public void privateFieldAccess() throws Exception {
        final String STUDENT_NAME = "siyoon";
        final int STUDENT_AGE = 8;

        Class<Student> clazz = Student.class;
        final Student student = clazz.getConstructor().newInstance();

        final Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, STUDENT_NAME);

        final Field age = clazz.getDeclaredField("age");
        age.setAccessible(true);
        age.set(student, STUDENT_AGE);

        assertThat(student.getName(), is(STUDENT_NAME));
        assertThat(student.getAge(), is(STUDENT_AGE));

        logger.debug(clazz.getName());
    }
}
