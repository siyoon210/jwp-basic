package core.di.factory.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static org.springframework.beans.BeanUtils.instantiateClass;

public class SetterInjector implements Injector {
    private static final Logger logger = LoggerFactory.getLogger(SetterInjector.class);

    private final BeanFactory beanFactory;

    public SetterInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        final Set<Method> injectedMethods = BeanFactoryUtils.getInjectedSetter(clazz);
        for (Method method : injectedMethods) {
            final Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new IllegalStateException("DI할 메소드는 인자가 하나여야 합니다.");
            }
            Class<?> concreteClazz = beanFactory.findConcreteClass(clazz);
            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(clazz);
            }
            try {
                method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
                logger.error(e.getMessage());
            }
        }
    }
}
