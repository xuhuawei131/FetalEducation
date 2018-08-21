package com.xuhuawei.love.fetaleducation.enums;

/**
 * Created by lingdian on 17/9/20.
 */

public enum TimerType {
    TIMER_TEST(1),//1分钟
    TIMER_30(30),//30分钟
    TIMER_60(60),//60分钟
    TIMER_90(90),//90分钟
    TIMER_120(120),//120分钟
    TIMER_END(1),//节目结束的时候
    TIMER_CANCEL(0);//取消结束
    public int timer;
    TimerType(int timer){
        this.timer=timer;
    }
}
