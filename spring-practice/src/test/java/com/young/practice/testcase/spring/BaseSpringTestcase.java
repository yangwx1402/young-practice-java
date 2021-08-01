package com.young.practice.testcase.spring;

import com.young.practice.Bootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public abstract class BaseSpringTestcase {

    private static final Object lock = new Object();

    protected ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        if (applicationContext != null) {
            return applicationContext;
        }
        synchronized (lock){
            if(applicationContext!=null) {
                return applicationContext;
            }else{
                applicationContext = SpringApplication.run(Bootstrap.class);
            }
        }
        return applicationContext;
    }

    public <T> T getBean(String beanName){
        return (T) getApplicationContext().getBean(beanName);
    }

    public <T> T getBean(Class<T> beanClass){
        return getApplicationContext().getBean(beanClass);
    }
}
