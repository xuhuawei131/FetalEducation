package com.xhwbaselibrary.lifecircle;

import android.content.Context;
import android.view.View;

/**
 * 生命周期的context
 * activity以及fragemnt进行统一
 * Created by xuhuawei on 2017/6/6 0006.
 */

public interface LifeCircleContext {
    /**
     * 获取生命周期 监听的presenter
     * @return
     */
    public LifeCircleCallback getLifeCircleCallbackPresenter();
    public Context getContext();


    public View findViewById(int viewId);
    public void showLoadingView();
    public void dismissLoadingView();
    public void finish();
}
