package com.qbea.service.calc.engine.config;

import com.qbea.service.calc.engine.persist.Persist;
import com.qbea.service.calc.engine.persist.impl.LocalCachePersist;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.Name;

/**
 */
@Configuration
public class PersistConfig {
    @Bean
    @ConditionalOnProperty(name="qbea.config.calc.persist.mode",havingValue = "local")
    public Persist localCachePersist(){
        return new LocalCachePersist();
    }

    @Bean
    @ConditionalOnMissingBean
    public Persist defaultPersist(){
        return new LocalCachePersist();
    }
}
