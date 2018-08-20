package com.xhwbaselibrary.lifecircle;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 生命周期监听
 * Created by xuhuawei on 2017/6/5 0005.
 */

public interface LifeCircleCallback {
    public Context getContext();
    /**
     * 添加生命周期监听
     * @param lifecycle
     */
    public void addLifecycleListener(@NonNull Lifecycle lifecycle) ;

    /**
     * 移除生命周期监听
     * @param lifecycle
     */
    public void removeLifecycleListener(@NonNull Lifecycle lifecycle) ;
}
