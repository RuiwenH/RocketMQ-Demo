package com.reven.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringUtil
 * @Description: 获取Bean实例的工具类
 */
@Component("cmopSpringUtil")
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * @Title: getApplicationContext
     * @Description: 获得Spring框架应用上下文对象
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @Title: getBean
     * @Description: 通过name获取 Bean
     * @param name
     *            spring的BeanID
     * @return Object
     * @throws Exception
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * @Title: getBean
     * @Description: 通过class获取Bean
     * @param clazz
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @Title: getBean
     * @Description: 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}