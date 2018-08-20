package com.xhwbaselibrary.lifecircle;

import android.content.Intent;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by 许华维 on 2017/6/5 0005.
 */

public class ActivityCallbackManager {

    private List<ActivityCallback> monitorList = new ArrayList<>();

    public void addCallback(ActivityCallback callback){
        monitorList.add(callback);
    }

    public void removeCallback(ActivityCallback callback){
        monitorList.remove(callback);
    }

    public void removeCallback(String callbackId){

        for(ActivityCallback callback : monitorList){
            if(callback.getCallbackId().equals(callbackId)){
                removeCallback(callback);
            }
        }
    }

    /**
     * 当ActivityResult时回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for(ActivityCallback callback : monitorList){
            callback.onActivityResult(requestCode,resultCode,data);
        }
    }


    public void onConfigurationChanged(Configuration newConfig) {
        for(ActivityCallback callback : monitorList){
            callback.onConfigurationChanged(newConfig);
        }
    }


    public void release(){
        monitorList.clear();
    }

}
