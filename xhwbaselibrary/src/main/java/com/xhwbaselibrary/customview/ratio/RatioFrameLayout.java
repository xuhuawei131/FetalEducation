package com.xhwbaselibrary.customview.ratio;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.xhwbaselibrary.R;


/**
 * Created by xuhuawei on 2017/12/29.
 */

public class RatioFrameLayout extends FrameLayout {
    private static int BASE_ON_WIDTH = 1;
    private static int BASE_ON_HEIGHT = 2;
    private float screen_widthRatio = -1;
    private float screen_heightRatio = -1;
    private float widthRatio = -1;
    private float heightRatio = -1;
    private float leftMargin = -1;
    private float topMargin = -1;
    private float rightMargin = -1;
    private float bottomMargin = -1;
    /**
     * 计算比例基于谁
     */
    private int baseOn = BASE_ON_WIDTH;

    public RatioFrameLayout(Context context) {
        super(context);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);//TypedArray是一个数组容器

        screen_widthRatio = typedArray.getFloat(R.styleable.RatioLayout_scrren_width_ratio, -1);
        screen_heightRatio = typedArray.getFloat(R.styleable.RatioLayout_scrren_height_ratio, -1);

        widthRatio = typedArray.getFloat(R.styleable.RatioLayout_width_ratio, -1);
        heightRatio = typedArray.getFloat(R.styleable.RatioLayout_height_ratio, -1);

        leftMargin = typedArray.getFloat(R.styleable.RatioLayout_leftMargin_ratio, -1);
        topMargin = typedArray.getFloat(R.styleable.RatioLayout_topMargin_ratio, -1);
        rightMargin = typedArray.getFloat(R.styleable.RatioLayout_rightMargin_ratio, -1);
        bottomMargin = typedArray.getFloat(R.styleable.RatioLayout_bottomMargin_ratio, -1);

        baseOn = typedArray.getInteger(R.styleable.RatioLayout_rl_ratio_base, BASE_ON_WIDTH);
        typedArray.recycle();

    }

    public RatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        if (widthRatio >= 1 && heightRatio >= 1) {

            // Children are just made to fill our space.

            int childWidthSize = getMeasuredWidth();
            int childHeightSize = getMeasuredHeight();

            float scale = 0;

            DisplayMetrics dm = getResources().getDisplayMetrics();
            if (baseOn == BASE_ON_WIDTH) {
                scale = dm.widthPixels / screen_widthRatio;
            } else if (baseOn == BASE_ON_HEIGHT) {
                scale = dm.heightPixels / screen_heightRatio;
            }

            childHeightSize = (int) (scale * heightRatio);
            childWidthSize = (int) (scale * widthRatio);

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    childWidthSize, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    childHeightSize, MeasureSpec.EXACTLY);


            MarginLayoutParams layoutParams = (MarginLayoutParams) this.getLayoutParams();
            if (layoutParams != null) {
                if (topMargin != -1) {
                    layoutParams.topMargin = (int) (scale * topMargin);
                }
                if (bottomMargin != -1) {
                    layoutParams.bottomMargin = (int) (scale * bottomMargin);
                }

                if (leftMargin != -1) {
                    layoutParams.leftMargin = (int) (scale * leftMargin);
                }

                if (rightMargin != -1) {
                    layoutParams.rightMargin = (int) (scale * rightMargin);
                }
                this.setLayoutParams(layoutParams);
            }

        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
