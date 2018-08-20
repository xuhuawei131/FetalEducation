package com.xhwbaselibrary.lifecircle;

import android.support.annotation.NonNull;

/**
 * 生命周期监听
 * Created by xuhuawei on 2017/6/5 0005.
 */

public abstract class Lifecycle {
    public static final int STATUS_CREATE = 0 ;
    public static final int STATUS_RESUME = 1 ;
    public static final int STATUS_PAUSE = 2 ;
    public static final int STATUS_STOP = 3 ;
    public static final int STATUS_DESTROY = 4 ;

    private String id ;
    private int status = STATUS_CREATE ;

    public Lifecycle(){}

    public Lifecycle(@NonNull String id){
        this.id = id ;
    }

    public int getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void onResume(){
        status = STATUS_RESUME;
        onLifecycleChange(status);
    }

    public void onPause(){
        status = STATUS_PAUSE;
        onLifecycleChange(status);
    }

    public void onStop(){
        status = STATUS_STOP;
        onLifecycleChange(status);
    }

    public void onDestroy(){
        status = STATUS_DESTROY;
        onLifecycleChange(status);
    }

    public abstract void onLifecycleChange(int status);

}
