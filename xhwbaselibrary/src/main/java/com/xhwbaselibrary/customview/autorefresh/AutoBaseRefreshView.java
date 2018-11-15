package com.xhwbaselibrary.customview.autorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class AutoBaseRefreshView extends Drawable implements Drawable.Callback, Animatable {

    private AutoPullToRefreshView mRefreshLayout;
    private boolean mEndOfRefreshing;

    public AutoBaseRefreshView(Context context, AutoPullToRefreshView layout) {
        mRefreshLayout = layout;
    }

    public Context getContext() {
        return mRefreshLayout != null ? mRefreshLayout.getContext() : null;
    }

    public AutoPullToRefreshView getRefreshLayout() {
        return mRefreshLayout;
    }

    public abstract void setPercent(float percent, boolean invalidate);

    public abstract void offsetTopAndBottom(int offset);

    @Override
    public void invalidateDrawable( Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    /**
     * Our animation depend on type of current work of refreshing.
     * We should to do different things when it's end of refreshing
     *
     * @param endOfRefreshing - we will check current state of refresh with this
     */
    public void setEndOfRefreshing(boolean endOfRefreshing) {
        mEndOfRefreshing = endOfRefreshing;
    }
}
