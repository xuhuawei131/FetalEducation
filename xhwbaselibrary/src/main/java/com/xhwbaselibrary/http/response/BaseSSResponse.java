package com.xhwbaselibrary.http.response;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.Fragment;

import com.xhwbaselibrary.http.request.XhwRequest;
import com.xhwbaselibrary.interfaces.LifeCircleContext;

import static com.xhwbaselibrary.lifecircle.Lifecycle.STATUS_DESTROY;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public abstract class BaseSSResponse {

    public abstract void onResultBack(String str);

    public abstract void onResultFailed(int code, String str);

    private XhwRequest xhwRequest;

    public void setRequest(XhwRequest xhwRequest) {
        this.xhwRequest = xhwRequest;
    }

    /**
     * 发送之前
     */
    public void beforeSend() {

    }

    /**
     * 结束之后
     */
    public void afterResponse() {

    }


    /**
     * 检测Activity是否可用，只支持4.2及以上。
     * 如果低于4.2则默认Activity可用
     *
     * @param activity
     * @return
     */
    public boolean isActivityOk(LifeCircleContext activity) {
        if (activity == null) {
            return false;
        }
        if (activity instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Activity realActivity = (Activity) activity;
                return !realActivity.isDestroyed();
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    /**
     * 检测Fragment是否可用
     *
     * @param context
     * @return
     */
    public boolean isFragmentOk(LifeCircleContext context) {

        if (context instanceof Fragment) {
            Fragment fragment = (Fragment) context;
            if (fragment == null) {
                return false;
            }

            if (fragment.getActivity() == null) {
                return false;
            }

            if (fragment.isDetached() || fragment.isRemoving()) {
                return false;
            }
        }


        return true;
    }

    /**
     * Context是否可用
     *
     * @param context
     * @return
     */
    public boolean isContextOk(Context context) {
        if (context == null) {
            return false;
        }

        return true;
    }

    /**
     * 是否在主线程运行
     *
     * @return
     */
    public boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 是否在子线程运行
     *
     * @return
     */
    public boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }

    /**
     * 判断是否要进行回调
     *
     * @return
     */
    public boolean canCallProxy() {

        if (isOnBackgroundThread()) {
            //如果当前线程不是主线程，则不允许调用
//            MageLog.e("Tag为" + xhwRequest.getTag() + "如果当前线程不是主线程，则不允许调用!");
            return false;
        }


        if (xhwRequest.getLifecycle() != null && xhwRequest.getLifecycle().getStatus() == STATUS_DESTROY) {
            //如果代理为空，则不允许调用
//            MageLog.e("Tag为" + xhwRequest.getTag() + "的请求载体生命周期已结束, 不予回调!");
            return false;
        }

        boolean result = isActivityOk(xhwRequest.getLifeCircleContext()) ||
                isFragmentOk(xhwRequest.getLifeCircleContext()) ||
                isContextOk(xhwRequest.getContext());

        if (!result) {
//            MageLog.i("Tag为" + xhwRequest.getTag() + "的请求 canCallProxy 判定结果为 " + result + "无法回调！");
        }
        return result;
    }

}
