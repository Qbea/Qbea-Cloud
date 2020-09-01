package com.qbea.service.calc.engine.config;

import com.qbea.common.web.custom.QbeaCustomClassloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 全局的classloader配置
 */
@Configuration
public class ClassLoaderConfig {
    private Logger logger = LoggerFactory.getLogger(ClassLoaderConfig.class);

    @Bean(name="extJarClassLoader")
    public QbeaCustomClassloader extJarClassLoader()throws Exception{
        QbeaCustomClassloader extJarClassLoader;
        File file = ResourceUtils.getFile("classpath:ext-jar/ext-model.jar");
        URL[] urls = new URL[1];
        urls[0]= file.toURL();
        extJarClassLoader = new QbeaCustomClassloader(urls);
        JarFile extJar = new JarFile(file);
        Enumeration<JarEntry> entries = extJar.entries();
        while (entries.hasMoreElements()){
            JarEntry jarEntry = entries.nextElement();
            String entryName = jarEntry.getName();
            if(entryName!= null && entryName.endsWith(".class")){
                Class<?> extClass = extJarClassLoader.loadClass(entryName.replace("/", ".").substring(0,entryName.length() - 6));
                logger.info("import ext class : {}",extClass);
            }
        }
        return extJarClassLoader;
    }
}
