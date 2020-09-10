package core.di.factory.injector;

import com.google.common.collect.Lists;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.beans.BeanUtils.instantiateClass;

public class ConstructorDependencyInjector implements DependencyInjector {
    private static final Logger logger = LoggerFactory.getLogger(ConstructorDependencyInjector.class);

    private final Set<Class<?>> preInstanticateBeans;
    private final Map<Class<?>, Object> beans;

    public ConstructorDependencyInjector(Set<Class<?>> preInstanticateBeans, Map<Class<?>, Object> beans) {
        this.preInstanticateBeans = preInstanticateBeans;
        this.beans = beans;
    }

    @Override
    public void inject(Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return;
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor == null) {
            return;
        }

        logger.debug("Constructor : {}", injectedConstructor);

        bean = instantiateConstructor(injectedConstructor);
        beans.put(clazz, bean);
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
            if (!preInstanticateBeans.contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
            }

            Object bean = beans.get(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return instantiateClass(constructor, args.toArray());
    }
}
