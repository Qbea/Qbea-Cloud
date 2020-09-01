package com.qbea.service.calc.engine.model;

import java.util.Date;

/**
 */
public class FlowData {
    private String id;
    private Date time;
    private String calcModelName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCalcModelName() {
        return calcModelName;
    }

    public void setCalcModelName(String calcModelName) {
        this.calcModelName = calcModelName;
    }
}
