package com.qbea.identity.dev.helper.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/*
    用于生成mybatis的mapper
    使用步骤：
    1.配置GeneratorConfig.properties
    2.配置generatorConfig.xml中的table标签
    3.运行方法

    @author radishT
 */
public class MybatisGenerator {
    public static void main(String[] args) throws Exception{
        generator();
    }
    private static void generator() throws Exception {
        File configFile = new ClassPathResource("generatorConfig.xml").getFile();
        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        warnings.forEach(item->{
            System.out.println(item);
        });
        System.out.println("代码生成完毕>>>>>>>>>>>>");
    }
}
