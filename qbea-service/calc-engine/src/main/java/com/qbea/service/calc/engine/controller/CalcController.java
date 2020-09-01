package com.qbea.service.calc.engine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
public class CalcController {
    Logger logger = LoggerFactory.getLogger(CalcController.class);


    @PostMapping("/pushFlowData")
    public String pushFlowData(@RequestBody Object object){
        return "success";
    }
}
