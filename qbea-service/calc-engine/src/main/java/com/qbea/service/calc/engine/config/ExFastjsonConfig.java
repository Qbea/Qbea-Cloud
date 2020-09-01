package com.qbea.service.calc.engine.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.qbea.common.web.custom.QbeaCustomClassloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 */
@Configuration
public class ExFastjsonConfig {
    @Autowired
    private FastJsonHttpMessageConverter defaultHttpMessageConverter;

    @Autowired
    @Qualifier("extJarClassLoader")
    private QbeaCustomClassloader extJarClassLoader;

    @Bean
    public HttpMessageConverters calcEngineHttpMessageConverter(){
        defaultHttpMessageConverter.getFastJsonConfig().getParserConfig().getGlobalInstance()
                .setDefaultClassLoader(extJarClassLoader);
        HttpMessageConverters ext = new HttpMessageConverters(defaultHttpMessageConverter);
        return ext;
    }
}
