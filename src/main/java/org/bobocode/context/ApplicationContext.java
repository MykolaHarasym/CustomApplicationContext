package org.bobocode.context;

import java.util.Map;

public interface ApplicationContext {

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String beanName, Class<T> beanType);

    <T> Map<String, Object> getBeans(Class<T> beanType);

}
