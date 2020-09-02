package com.qbea.service.calc.engine.script.impl;

import com.qbea.service.calc.engine.action.impl.CountOneResultAction;
import com.qbea.service.calc.engine.script.CalcScript;

/**
 */
public class TestCalcScript extends CalcScript {
    public TestCalcScript(){
        this.calcModelName("com.alibaba.fastjson.JSONObject")
                .objKey("id")
                .timeKey("createTime")
                .mergeAction(new CountOneResultAction(2L));
    }
}
