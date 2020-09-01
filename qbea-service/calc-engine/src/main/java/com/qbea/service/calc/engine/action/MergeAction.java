package com.qbea.service.calc.engine.action;

/**
 * 流式数据和计算结果的merge方式
 */
public interface MergeAction {
    void merge(Object data,Object indiResult);
}
