package ru.itpark.common.cached;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;
import ru.itpark.common.model.Model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class CachedBeanPostProcessor implements BeanPostProcessor {
    private final Map<Method, Map<Object, Model>> cache = new HashMap<>();
    private final Map<Object, Model> modelMap = new HashMap<>();
    private final Map<String, Class> classMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Cached.class)) {
            classMap.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!classMap.containsKey(beanName)) {
            return bean;
        }

        var enhancer = new Enhancer();
        enhancer.setSuperclass(classMap.get(beanName));
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if (cache.containsKey(method)) {
                    Map<Object, Model> cacheModel = cache.get(method);
                    if (cacheModel.containsKey(objects[0]))
                        return cacheModel.get(objects[0]);
                }

                Model res = (Model) methodProxy.invoke(bean, objects);
                if (res == null)
                    return null;
                modelMap.put(objects[0], res);
                cache.put(method, modelMap);
                return res;
            }
        });

        return enhancer.create();
    }

}