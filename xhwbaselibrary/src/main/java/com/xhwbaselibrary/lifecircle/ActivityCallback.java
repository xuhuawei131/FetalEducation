package com.xhwbaselibrary.lifecircle;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

/**
 * Created by 许华维 on 2017/6/5 0005.
 */

public class ActivityCallback {

    private String callbackId ;

    public ActivityCallback(){

    }

    public ActivityCallback(@NonNull String callbackId){
        this.callbackId = callbackId ;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }


    /**
     * 当ActivityResult时回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    public void onConfigurationChanged(Configuration newConfig) {

    }

}
