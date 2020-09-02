package com.qbea.service.calc.engine.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qbea.service.calc.engine.core.CalcEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
public class CalcController {
    Logger logger = LoggerFactory.getLogger(CalcController.class);
    @Autowired
    private CalcEngine calcEngine;


    @PostMapping("/pushFlowData")
    public String pushFlowData(@RequestBody Object flowData){
        if(flowData!=null){
            calcEngine.pushFlowData(flowData);
        }
        return "success";
    }
}
