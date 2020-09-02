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
    /** 流计算方式 */
    private MergeAction mergeAction;
    /** 指标名称 */
    private String indiName;

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

    public CalcScript indiName(String indiName){
        this.indiName = indiName;
        return this;
    }

    public MergeAction getMergeAction() {
        return mergeAction;
    }

    public String getIndiName() {
        return indiName;
    }
}
