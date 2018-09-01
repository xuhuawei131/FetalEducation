package com.xhwbaselibrary.customview.refreshview;

import android.content.Context;
import android.util.AttributeSet;

import com.xhwbaselibrary.customview.refreshview.drawables.HWRefreshDrawable;
import com.xhwbaselibrary.customview.refreshview.drawables.HWRefreshDrawable2;
import com.xhwbaselibrary.customview.refreshview.drawables.HWRefreshDrawable3;
import com.xhwbaselibrary.customview.refreshview.drawables.HWRefreshDrawable4;
import com.xhwbaselibrary.customview.refreshview.drawables.HWRefreshDrawable5;


/**
 * Created by xuhuawei on 16/4/14.
 */
public class HWRefreshLayout extends PullToRefreshView {

    private BaseRefreshView drawable4;

    public HWRefreshLayout(Context context) {
        this(context,null);
    }

    public HWRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        drawable4 = new HWRefreshDrawable4(context, this);
//        drawable4 = new HWRefreshDrawable(context, this);
//        drawable4 = new HWRefreshDrawable2(context, this);
//        drawable4 = new HWRefreshDrawable3(context, this);
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
