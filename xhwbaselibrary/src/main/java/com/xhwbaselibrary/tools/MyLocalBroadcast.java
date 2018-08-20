package com.xhwbaselibrary.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.xhwbaselibrary.caches.MyAppContext;

/**
 * Created by lingdian on 17/9/26.
 * 我的自定义localbroadcast
 */

public class MyLocalBroadcast {
    private static MyLocalBroadcast instance;
    private Context context;
    private MyLocalBroadcast(){
        context= MyAppContext.getInstance().getContext();
    }
    public static MyLocalBroadcast getInstance(){
        if (instance==null){
            instance=new MyLocalBroadcast();
        }
        return instance;
    }

    public void register(IntentFilter intentFilter, BroadcastReceiver receiver){
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver,intentFilter);
    }
    public void unRegister( BroadcastReceiver receiver){
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }
    public void setBroadcast(Intent intent){
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
