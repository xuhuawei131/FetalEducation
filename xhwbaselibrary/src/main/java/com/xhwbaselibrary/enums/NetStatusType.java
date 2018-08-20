package com.xhwbaselibrary.enums;

/**
 * Created by lingdian on 17/9/26.
 * 网络的状态
 */

public enum NetStatusType {
    NETSTATUS_NONE(0),NETSTATUS_WIFI(1),NETSTATUS_MOBILE(2);
    public int type;
    NetStatusType(int type){
        this.type=type;
    }
}
