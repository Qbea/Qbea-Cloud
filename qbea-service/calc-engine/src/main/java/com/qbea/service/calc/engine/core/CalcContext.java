package com.qbea.service.calc.engine.core;

import com.qbea.common.web.custom.QbeaCustomClassloader;
import com.qbea.service.calc.engine.script.CalcScript;
import com.qbea.service.calc.engine.script.impl.TestCalcScript;
import com.sun.javaws.Launcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import sun.reflect.Reflection;

import javax.annotation.PostConstruct;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 计算脚本上下文
 */
@Component
public class CalcContext {
    Logger logger = LoggerFactory.getLogger(CalcContext.class);
    /**
     * 指标名称 to 计算脚本   每笔流水会过一遍所有的脚本
     */
    public static final Map<String,CalcScript> CALC_SCRIPT_MAP = new HashMap<>();

    public static final Map<String,BigDecimal> CALC_INDI_RESULT_MAP = new HashMap<>();

    private QbeaCustomClassloader extJarClassLoader;

    private QbeaCustomClassloader globalClassLoader;
    /**
     * 初始化
     */
    @PostConstruct
    public void init()throws Exception{
        // 模拟订阅到一个脚本
        CALC_SCRIPT_MAP.put("indi1",new TestCalcScript());
        // 模拟订阅到一个模型包
        loadExtJar();
    }

    private void loadExtJar()throws Exception{
        //从决策平台订阅model包
        takeModelJar();
    }

    /**
     * 从决策平台订阅ext-model.jar(待实现)
     */
    private void takeModelJar(){

    }

}
