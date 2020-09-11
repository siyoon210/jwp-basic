package core.di.factory.injector;

import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;


public class FieldDependencyInjector implements DependencyInjector {
    private static final Logger logger = LoggerFactory.getLogger(FieldDependencyInjector.class);

    private final Set<Class<?>> preInstanticateBeans;
    private final Map<Class<?>, Object> beans;

    public FieldDependencyInjector(Set<Class<?>> preInstanticateBeans, Map<Class<?>, Object> beans) {
        this.preInstanticateBeans = preInstanticateBeans;
        this.beans = beans;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
    }

    private Object instantiateClass(Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return bean;
        }

        final Set<Field> injectedFields = BeanFactoryUtils.getInjectedFields(clazz);
        bean = BeanUtils.instantiate(clazz);
        if (injectedFields.isEmpty()) {
            beans.put(clazz, bean);
            return bean;
        }

        for (Field injectedField : injectedFields) {
            try {
                injectedField.setAccessible(true);
                injectedField.set(instantiateClass(injectedField.getDeclaringClass()), bean);
            } catch (IllegalAccessException e) {
                logger.info("{}", e.getMessage());
            }
        }

        beans.put(clazz, bean);
        return bean;
    }

//    private Object instantiateConstructor(Constructor<?> constructor) {
//        Class<?>[] pTypes = constructor.getParameterTypes();
//        List<Object> args = Lists.newArrayList();
//        for (Class<?> clazz : pTypes) {
//            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
//            if (!preInstanticateBeans.contains(concreteClazz)) {
//                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
//            }
//
//            Object bean = beans.get(concreteClazz);
//            if (bean == null) {
//                bean = instantiateClass(concreteClazz);
//            }
//            args.add(bean);
//        }
//        return BeanUtils.instantiateClass(constructor, args.toArray());
//    }
}
