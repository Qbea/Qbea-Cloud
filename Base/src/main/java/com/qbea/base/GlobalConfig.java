package com.qbea.base;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * 　@author radishT
 * 　@date: 2020/1/2 10:14
 *
 */
@Component
public class GlobalConfig implements EnvironmentAware {
    private static Environment env;
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    /**
     * 获取配置
     * @param key
     * @return
     */
    public static String getProperty(String key){
        String property = env.getProperty(key);
        return property;
    }

    public static <T> T getProperty(String key, Class<T> type){
        return env.getProperty(key,type);
    }
}
