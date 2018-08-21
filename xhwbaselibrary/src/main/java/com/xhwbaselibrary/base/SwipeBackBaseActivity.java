package com.xhwbaselibrary.base;

import android.os.Bundle;
import android.view.View;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by lingdian on 17/9/20.
 */

public abstract class SwipeBackBaseActivity extends BaseActivity {
    protected SwipeBackActivityHelper mHelper;


    protected abstract void initSwiper();

    public SwipeBackBaseActivity() {

    }
    @Override
    protected void init() {
        this.mHelper = new SwipeBackActivityHelper(this);
        this.mHelper.onActivityCreate();
        initSwiper();
    }


    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mHelper.onPostCreate();
    }

    public View findViewById(int id) {
        View v = super.findViewById(id);
        return v == null && this.mHelper != null?this.mHelper.findViewById(id):v;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mHelper.getSwipeBackLayout();
    }

    public void setSwipeBackEnable(boolean enable) {
        this.getSwipeBackLayout().setEnableGesture(enable);
    }

    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        this.getSwipeBackLayout().scrollToFinishActivity();
    }
}
