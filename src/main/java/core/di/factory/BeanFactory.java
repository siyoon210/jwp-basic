package core.di.factory;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> preInstanticateBean : preInstanticateBeans) {
            beans.put(preInstanticateBean, createBean(preInstanticateBean));
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T createBean(Class<T> beanType) {
        //인터페이스가 들어온 경우
        final Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(beanType, preInstanticateBeans);

        if (hasBean(concreteClass)) {
            getBean(concreteClass);
        }

        //기본생성자이거나, @Inject 명시후에 파라미터가 없는경우
        final Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);
        if (constructor == null) {
            try {
                return (T)concreteClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }

        // @Inject 명시하고 파라미터가 있는경우
        final Class<?>[] constructorParameterTypes = constructor.getParameterTypes();
        final Object[] parameterObjects = new Object[constructorParameterTypes.length];
        for (int i = 0; i < constructorParameterTypes.length; i++) {
            parameterObjects[i] = createBean(constructorParameterTypes[i]);
        }

        try {
            return (T) constructor.newInstance(parameterObjects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> boolean hasBean(Class<T> concreteClass) {
        return beans.containsKey(concreteClass);
    }


}
