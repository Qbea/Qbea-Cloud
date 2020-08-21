package com.qbea.rule.engine.test;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;


/**
 */
public class AviatorTest {
    @Test
    public void test1() throws Exception {
        // Compile the script into a Expression instance.
        Expression exp = AviatorEvaluator.getInstance().compileScript("AviatorScripts/hello.av");
        // Run the exprssion.
        exp.execute();
    }
    @Test
    public void test2() throws Exception {
        // Compile the script into a Expression instance.
        Expression exp = AviatorEvaluator.getInstance().compileScript("AviatorScripts/functionTest.av");
        // Run the exprssion.
        Object execute = exp.execute();
        System.out.println(execute.toString());
    }
}
