package com.qbea.service.calc.engine.action.impl;

import com.qbea.service.calc.engine.action.LongMergeAction;
import com.qbea.service.calc.engine.action.MergeAction;

import java.util.List;

/**
 */
public class CountOneResultAction implements LongMergeAction {
    private Long t ;
    public CountOneResultAction(Long t){
        this.t = t;
    }
    /**
     * @param data             当前流水数据
     * @param indiResultBefore 计算前的指标结果  (有可能为null,即第一次计算的情况)
     * @param historyList      之前的历史流水数据
     * @return 指标的最新计算结果
     */
    @Override
    public Long merge(Object data, Long indiResultBefore, List<Object> historyList) {
        if(indiResultBefore==null){
            indiResultBefore = 0L;
        }
        return indiResultBefore+t;
    }
}
