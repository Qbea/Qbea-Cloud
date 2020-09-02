package com.qbea.service.calc.engine.persist;

import java.util.List;

/**
 * 流计算引擎需要保存一些数据，但是实现可能是redis,db等实现，这是抽象
 */
public interface Persist {
    /**
     * 保存一笔流水数据(一定时间后过期)，适用于一些统计时间窗口类的指标
     * @param flowData 流水数据
     * @param expire 过期毫秒数
     */
    void storeFlowDataWithExpire(Object flowData,int expire);

    /**
     * 永久保存一笔流水
     * @param flowData 流水数据
     */
    void storeFlowData(Object flowData);

    /**
     * 保留最近的N笔流水
     * @param flowData 流水数据
     * @param limit 保存N笔最近的数据
     */
    void storeFlowDataRecently(Object flowData,int limit);

    /**
     * 根据类的全限定名读取这个类的历史数据
     *
     * @param className
     * @return 历史的流水数据
     */
    List<Object> readHistoryFlowData(String className);
}
