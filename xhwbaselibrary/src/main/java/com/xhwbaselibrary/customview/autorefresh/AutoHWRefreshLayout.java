package com.xhwbaselibrary.customview.autorefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.xhwbaselibrary.customview.autorefresh.drawable.HWRefreshDrawable5;


/**
 * Created by xuhuawei on 16/4/14.
 */
public class AutoHWRefreshLayout extends AutoPullToRefreshView {

    private AutoBaseRefreshView drawable4;

    public AutoHWRefreshLayout(Context context) {
        this(context,null);
    }

    public AutoHWRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawable4 = new HWRefreshDrawable5(context, this);
        setRefreshStyle(drawable4);
    }

    /**
     * 设置自动刷新
     */
    public void setAutoRefreshView() {
        setRefreshing(true);
        notifiyRefreshListener();
    }

    public void setPaintColor(int color) {
        if (drawable4 != null) {
//            drawable4.setPaintColor(color);
        }
    }


    public void setRefreshBackground(int color) {
        if (drawable4 != null) {
//            drawable4.setRefreshBackground(color);
        }
    }

}
