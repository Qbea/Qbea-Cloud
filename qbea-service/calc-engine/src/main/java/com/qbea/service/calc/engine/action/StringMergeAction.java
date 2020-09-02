package com.qbea.service.calc.engine.action;

import java.util.List;

/**
 */
public interface StringMergeAction {

    /**
     *
     * @param data 当前流水数据
     * @param indiResultBefore 计算前的指标结果
     * @param historyList 之前的历史流水数据
     *
     *  @return 指标的最新计算结果
     */
    String merge(Object data, String indiResultBefore, List<Object> historyList);
}
