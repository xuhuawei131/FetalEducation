package com.xhwbaselibrary.customview.refreshview;

import android.content.Context;
import android.util.AttributeSet;

import com.xhwbaselibrary.customview.refreshview.drawables.HWRefreshDrawable4;


/**
 * Created by xuhuawei on 16/4/14.
 */
public class HWRefreshLayout extends PullToRefreshView {

    private HWRefreshDrawable4 drawable4 ;

    public HWRefreshLayout(Context context) {
        super(context);
        drawable4 = new HWRefreshDrawable4(context, this);
        setRefreshStyle(drawable4);
    }

    public HWRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawable4 = new HWRefreshDrawable4(context, this);
        setRefreshStyle(drawable4);
    }

    public void setPaintColor(int color){
        if(drawable4 != null){
            drawable4.setPaintColor(color);
        }
    }


    public void setRefreshBackground(int color){
        if(drawable4 != null){
            drawable4.setRefreshBackground(color);
        }
    }

}
