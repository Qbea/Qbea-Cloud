package com.qbea.service.calc.engine.script;

import com.qbea.service.calc.engine.action.MergeAction;

import java.util.Date;

/**
 *
 */
public class CalcScript {
    private String calcModelName;
    private String objKey;
    private String timeKey;
    private MergeAction mergeAction;

    public CalcScript calcModelName(String calcModelName){
        this.calcModelName = calcModelName;
        return this;
    }


    public CalcScript objKey(String objKey){
        this.objKey = objKey;
        return this;
    }

    public CalcScript timeKey(String timeKey){
        this.timeKey = timeKey;
        return this;
    }
    public CalcScript mergeAction(MergeAction mergeAction){
        this.mergeAction = mergeAction;
        return this;
    }

    public String getCalcModelName() {
        return calcModelName;
    }

    public String getObjKey() {
        return objKey;
    }

    public String getTimeKey() {
        return timeKey;
    }
}
