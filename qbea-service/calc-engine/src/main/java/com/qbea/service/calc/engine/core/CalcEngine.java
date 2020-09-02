package com.qbea.service.calc.engine.core;

import com.qbea.service.calc.engine.action.BigDecimalMergeAction;
import com.qbea.service.calc.engine.action.LongMergeAction;
import com.qbea.service.calc.engine.action.MergeAction;
import com.qbea.service.calc.engine.action.StringMergeAction;
import com.qbea.service.calc.engine.persist.Persist;
import com.qbea.service.calc.engine.script.CalcScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 计算引擎
 */
@Component
public class CalcEngine {
    @Autowired
    private CalcContext calcContext;

    @Autowired
    private Persist persist;

    /**
     * 推送一笔流水数据
     * - 获取这些同类流水的历史数据
     * - 保存此次数据
     * - 计算相关指标结果
     * @param thisFlowData
     */
    public void pushFlowData(Object thisFlowData){
        String className = thisFlowData.getClass().getName();
        // 如果有这个类的计算脚本
        // todo 这里后续要考虑这类的父类的计算脚本
        if(calcContext.CALC_SCRIPT_MAP.containsKey(className)){
            // 获取历史的流水
            List<Object> historyFlowData = persist.readHistoryFlowData(className);

            List<CalcScript> calcScripts = calcContext.CALC_SCRIPT_MAP.get(className);
            for (CalcScript calcScript : calcScripts) {
                // 获取上一次的指标结果（可能是第一次计算-null）
                Object preIndiResult = calcContext.CALC_INDI_RESULT_MAP.get(calcScript.getIndiName());
                calcIndi(preIndiResult,thisFlowData,calcScript,historyFlowData);
            }
            // 计算脚本全部计算完成后，保存当前流水
            persist.storeFlowData(thisFlowData);
        }
    }

    /**
     * 根据一个对象，一个脚本，和一批历史推送的流水数据
     * @param preIndiResult
     * @param flowData
     * @param calcScript
     * @param historyList
     */
    private void calcIndi(Object preIndiResult,Object flowData,CalcScript calcScript,List<Object> historyList){
        Object newMergeResult = null;
        MergeAction mergeAction = calcScript.getMergeAction();
        if(mergeAction instanceof BigDecimalMergeAction){
            newMergeResult = ((BigDecimalMergeAction) mergeAction).merge(flowData, (BigDecimal) preIndiResult, historyList);
        }else if(mergeAction instanceof LongMergeAction){
            newMergeResult = ((LongMergeAction)mergeAction).merge(flowData,(Long) preIndiResult,historyList);
        }else if(mergeAction instanceof StringMergeAction){
            newMergeResult = ((StringMergeAction)mergeAction).merge(flowData,(String)preIndiResult,historyList);
        }
        if(newMergeResult != null){
            calcContext.CALC_INDI_RESULT_MAP.put(calcScript.getIndiName(),newMergeResult);
            System.out.println(newMergeResult);
        }
    }
}
