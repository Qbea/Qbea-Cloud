package com.qbea.common.web.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.Charset;

/**
 * 注入基础的fastJsonHttpMessageConverter,如果组件需要定制，必须基于这个bean定制
 */
@Configuration
public class FastjsonConfig {
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setDateFormat("yyyy-MM-dd hh:mm:ss");
        fastJsonConfig.getParserConfig().getGlobalInstance().setAutoTypeSupport(true);
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }
}
