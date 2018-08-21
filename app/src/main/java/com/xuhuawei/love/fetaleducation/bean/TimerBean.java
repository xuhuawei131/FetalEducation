package com.xuhuawei.love.fetaleducation.bean;

import com.xuhuawei.love.fetaleducation.enums.TimerType;

import java.io.Serializable;

/**
 * Created by lingdian on 17/9/22.
 */

public class TimerBean implements Serializable {
    public boolean isSelect=false;
    public String name;
    public TimerType timerType;

    public TimerBean(String name, TimerType timerType, boolean isSelect){
        this.name=name;
        this.isSelect=isSelect;
        this.timerType=timerType;
    }
}
