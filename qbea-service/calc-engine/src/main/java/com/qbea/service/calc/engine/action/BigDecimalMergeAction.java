package com.qbea.service.calc.engine.action;

import java.math.BigDecimal;
import java.util.List;

/**
 */
public interface BigDecimalMergeAction extends MergeAction{

    /**
     *
     * @param data 当前流水数据
     * @param indiResultBefore 计算前的指标结果
     * @param historyList 之前的历史流水数据
     *
     *  @return 指标的最新计算结果
     */
    BigDecimal merge(Object data, BigDecimal indiResultBefore, List<Object> historyList);
}
