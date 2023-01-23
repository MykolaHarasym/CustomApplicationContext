package org.bobocode.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bobocode.annotation.Bean;
import org.bobocode.exceptions.NoSuchBeanException;
import org.bobocode.exceptions.NoUniqueBeanException;
import org.reflections.Reflections;

import lombok.SneakyThrows;

public class AnnotationConfigurationApplicationContext implements ApplicationContext {

    private Map<String, Object> beanContainer = new HashMap<>();

    public AnnotationConfigurationApplicationContext(String packageName) {
        scanAndCreateBean(packageName);
    }

    private void scanAndCreateBean(String packageName) {
        Reflections reflections = new Reflections(packageName);
        reflections.getTypesAnnotatedWith(Bean.class)
                .forEach(this::registerBean);
    }

    @SneakyThrows
    private void registerBean(Class<?> type) {
        var beanAnnotation = type.getAnnotation(Bean.class);
        var beanId = beanIdResolver(beanAnnotation.value(), type);
        var constructor = type.getConstructor();
        var beanInstance = constructor.newInstance();
        beanContainer.put(beanId, beanInstance);
    }

    private String beanIdResolver(String value, Class<?> type) {
        if (Objects.nonNull(value) && !value.isEmpty()) {
            return uncapitalize(value);
        } else return uncapitalize(type.getSimpleName());
    }

    private String uncapitalize(String value) {
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }

    @Override
    public <T> T getBean(Class<T> type) {
        var beans = beanContainer.values().stream()
                .filter(type::isInstance)
                .toList();

        if (beans.size() > 1) {
            throw new NoUniqueBeanException("More than one bean is found. Please, specify a beanName");
        }
        if (beans.isEmpty()) {
            throw new NoSuchBeanException(String.format("Bean with type '%s' is not found!",
                    type.getSimpleName()));
        }

        return type.cast(beans.get(0));
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) {
        var bean = beanContainer.get(beanName);

        checkIsBeanNotNull(bean, beanName);

        return beanType.cast(bean);
    }

    @Override
    public <T> Map<String, Object> getBeans(Class<T> beanType) {
        return beanContainer.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }

    private void checkIsBeanNotNull(Object bean, String beanName) {
        if (Objects.isNull(bean)) {
            throw new NoSuchBeanException(String.format("Bean with name '%s' is not found", beanName));
        }
    }
}
