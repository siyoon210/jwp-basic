package core.di.factory.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

import static org.springframework.beans.BeanUtils.instantiateClass;

public class ConstructorInjector implements Injector {
    private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);

    private final BeanFactory beanFactory;

    public ConstructorInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        final Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        Class<?> concreteClazz = beanFactory.findConcreteClass(clazz);
        Object bean = beanFactory.getBean(concreteClazz);
    }
}
