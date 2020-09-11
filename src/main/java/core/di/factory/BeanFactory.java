package core.di.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.di.factory.injector.ConstructorDependencyInjector;
import core.di.factory.injector.DependencyInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    //    private Set<DependencyInjector> dependencyInjectors = Sets.newHashSet();
    private DependencyInjector dependencyInjector = new ConstructorDependencyInjector(preInstanticateBeans, beans);

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        for (Class<?> clazz : preInstanticateBeans) {
            if (beans.get(clazz) == null) {
                logger.debug("instantiated Class : {}", clazz);
                createBeanInstance(clazz);
            }
        }
    }

    private Object createBeanInstance(Class<?> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return bean;
        }

//        //생성자에 @Inject가 있는경우
        final Constructor<?> injectAnnotatedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectAnnotatedConstructor != null) {
            logger.debug("Constructor : {}", injectAnnotatedConstructor);
            bean = createBeanWithConstructorDi(injectAnnotatedConstructor);
            beans.put(clazz, bean);
            return bean;

        }

//        // 세터에 @Inject가 있는 경우
//        final Set<Method> injectedSetter = BeanFactoryUtils.getInjectedSetter(clazz);
//        if (!injectedSetter.isEmpty()) {
//            for (Method method : injectedSetter) {
//
//            }
//        }
//
//        // 필드에 @Inject가 있는 경우
//        final Set<Field> injectAnnotatedFields = BeanFactoryUtils.getInjectedFields(clazz);
//        if (!injectAnnotatedFields.isEmpty()) {
//            bean = createBeanWithFieldDi(clazz, injectAnnotatedFields);
//            return beans.put(clazz, bean);
//        }

        bean = BeanUtils.instantiate(clazz);
        beans.put(clazz, bean);
        return bean;
    }

    private Object createBeanWithConstructorDi(Constructor<?> injectAnnotatedConstructor) {
        Class<?>[] pTypes = injectAnnotatedConstructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
            if (!preInstanticateBeans.contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
            }

            Object bean = beans.get(concreteClazz);
            if (bean == null) {
                bean = createBeanInstance(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(injectAnnotatedConstructor, args.toArray());
    }

    private Object createBeanWithFieldDi(Class<?> clazz, Set<Field> injectAnnotatedFields) {
        Object bean;
        bean = BeanUtils.instantiate(clazz);
        for (Field injectedField : injectAnnotatedFields) {
            try {
                injectedField.setAccessible(true);
                injectedField.set(bean, createBeanInstance(injectedField.getDeclaringClass()));
            } catch (IllegalAccessException | IllegalArgumentException e) {
                logger.error(e.getMessage());
            }
        }
        return bean;
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : preInstanticateBeans) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }

    public Class<?> findConcreteClass(Class<?> clazz) {
        return BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
    }
}
