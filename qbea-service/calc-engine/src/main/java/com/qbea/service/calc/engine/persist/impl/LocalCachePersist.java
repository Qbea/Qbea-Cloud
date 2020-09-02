package com.qbea.service.calc.engine.persist.impl;

import com.qbea.service.calc.engine.persist.Persist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class LocalCachePersist implements Persist {
    // @todo 注意并发控制
    private ConcurrentHashMap<String,List<Object>> localCacheMap = new ConcurrentHashMap<>();

    /**
     * @todo 待实现
     * 保存一笔流水数据(一定时间后过期)，适用于一些统计时间窗口类的指标
     *
     * @param flowData 流水数据
     * @param expire   过期毫秒数
     */
    @Override
    public void storeFlowDataWithExpire(Object flowData, int expire) {

    }

    /**
     * 永久保存一笔流水
     *
     * @param flowData 流水数据
     */
    @Override
    public void storeFlowData(Object flowData) {
        if(flowData!=null){
            String className = flowData.getClass().getName();
            if(localCacheMap.containsKey(className)){
                localCacheMap.get(className).add(flowData);
            }else{
                localCacheMap.put(className,new ArrayList<>());
            }
        }
    }

    /**
     * @todo 待实现
     * 保留最近的N笔流水
     *
     * @param flowData 流水数据
     * @param limit    保存N笔最近的数据
     */
    @Override
    public void storeFlowDataRecently(Object flowData, int limit) {

    }


    /**
     * 根据类的全限定名读取这个类的历史数据
     *
     * @param className
     * @return
     */
    @Override
    public List<Object> readHistoryFlowData(String className) {
        if(className==null){return null;}
        List<Object> objects = localCacheMap.get(className);
        if(objects == null){
            objects=new ArrayList<>();
        }
        return objects;
    }
}
