package core.di.factory.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class SetterDependencyInjector implements DependencyInjector {
    private static final Logger logger = LoggerFactory.getLogger(SetterDependencyInjector.class);

    private final Set<Class<?>> preInstanticateBeans;
    private final Map<Class<?>, Object> beans;

    public SetterDependencyInjector(Set<Class<?>> preInstanticateBeans, Map<Class<?>, Object> beans) {
        this.preInstanticateBeans = preInstanticateBeans;
        this.beans = beans;
    }

    @Override
    public void inject(Class<?> clazz) {
//        instantiateClass(clazz);
//        final Set<Method> injectedMethods = BeanFactoryUtils.getInjectedSetter(clazz);
//        for (Method method : injectedMethods) {
//            final Class<?>[] parameterTypes = method.getParameterTypes();
//            if (parameterTypes.length != 1) {
//                throw new IllegalStateException("DI할 메소드는 인자가 하나여야 합니다.");
//            }
//            Class<?> concreteClazz = beanFactory.findConcreteClass(clazz);
//            Object bean = beanFactory.getBean(concreteClazz);
//            if (bean == null) {
//                bean = instantiateClass(clazz);
//            }
//            try {
//                method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
//            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
//                logger.error(e.getMessage());
//            }
//        }
    }
}
