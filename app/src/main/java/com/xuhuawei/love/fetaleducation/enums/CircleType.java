package com.xuhuawei.love.fetaleducation.enums;

public enum CircleType {
    NONE_CIRCLE(0),SINGLE_CIRCLE(1),LIST_CIRCLE(2),LIST_NONE_CIRCLE(3),RANDOM_CRICLE(4);
    public int type;
     CircleType(int type){
        this.type=type;
    }
    public static CircleType getCircleType(int type){
         if (type==0){
             return NONE_CIRCLE;
         }else  if (type==1){
             return SINGLE_CIRCLE;
         }else  if (type==2){
             return LIST_CIRCLE;
         }else  if (type==3){
             return LIST_NONE_CIRCLE;
         }else  if (type==4){
             return RANDOM_CRICLE;
         }else{
             return NONE_CIRCLE;
         }
    }
}
