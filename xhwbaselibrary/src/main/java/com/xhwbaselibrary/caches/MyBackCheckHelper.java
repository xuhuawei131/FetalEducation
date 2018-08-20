package com.xhwbaselibrary.caches;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by lingdian on 17/9/25.
 * 检测前台助手
 */

public class MyBackCheckHelper {
    private static MyBackCheckHelper instance=null;
    private boolean isBackground = true;
    // 上下文对象
    private Context mContext = null;

    private Method mReflectScreenState;
    private PowerManager mPowerManager;

    private MyBackCheckHelper(){
        mContext=MyAppContext.getInstance().getContext();

        mPowerManager = (PowerManager) mContext.getSystemService(Activity.POWER_SERVICE);
        try {
            isBackground = true;
            mReflectScreenState = PowerManager.class.getMethod("isScreenOn",
                    new Class[] {});
        } catch (NoSuchMethodException nsme) {

        }
        MyAppContext.getInstance().getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    public static MyBackCheckHelper getInstance(){
        if(instance==null){
            instance=new MyBackCheckHelper();
        }
        return instance;
    }

    /**
     * 是否在后台运行
     * @return
     */
    public boolean isBackground() {
        return isBackground;
    }

    /**
     * App前后状态变化
     */
    private void setAppState(boolean foreground){
        if (foreground) {
            // 前台
            isBackground = false;
        } else {
            // 后台
            isBackground = true;
        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    private boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = mContext.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
    /**
     * screen是否打开状态
     * @return
     */
    private boolean isScreenOn() {
        boolean screenState = true;
        try {
            if(mReflectScreenState != null) {
                screenState = (Boolean) mReflectScreenState.invoke(mPowerManager);
            }
        } catch (Exception e) {
            screenState = true;
        }
        return screenState;
    }



    /**
     * 生命周期监听回调
     */
    private Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if(!isScreenOn()){
                // 当锁屏状态时，App状态为后台状态
                setAppState(false);
            }else{
                setAppState(true);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if(!isScreenOn()){
                // 当锁屏状态时，App状态为后台状态
                setAppState(false);
            }else {
                if (isAppOnForeground()) {
                    setAppState(true);
                } else {
                    setAppState(false);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}
