package com.xhwbaselibrary.caches;

import android.app.Application;
import android.content.Context;

/**
 * Created by lingdian on 17/9/25.
 * 应用application的Context
 *
 */

public class MyAppContext {
    private Application application;
    private static final MyAppContext ourInstance = new MyAppContext();

    public static MyAppContext getInstance() {
        return ourInstance;
    }

    private MyAppContext() {

    }
    public void init(Application application){
        this.application=application;
    }
    public Application getApplication(){
        return application;
    }
    public Context getContext(){
        return getApplication().getApplicationContext();
    }
}
