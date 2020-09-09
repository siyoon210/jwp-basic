package core.di.factory.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

import static org.springframework.beans.BeanUtils.instantiateClass;


public class FieldInjector implements Injector {
    private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

    private BeanFactory beanFactory;

    public FieldInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        final Set<Field> injectedFields = BeanFactoryUtils.getInjectedFields(clazz);
        for (Field field : injectedFields) {
            Class<?> concreteClazz = beanFactory.findConcreteClass(clazz);
            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(clazz);
            }
            try {
                field.setAccessible(true);
                field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
