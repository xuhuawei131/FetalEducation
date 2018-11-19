package com.xhwbaselibrary.customview.ratio;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xhwbaselibrary.R;


/**
 * Created by xuhuawei on 2017/12/29.
 */

public class RatioImageView extends ImageView {
    private static int BASE_ON_WIDTH = 1;
    private static int BASE_ON_HEIGHT = 2;
    private float widthRatio = -1;
    private float heightRatio = -1;
    /**
     * 计算比例基于谁
     */
    private int baseOn = BASE_ON_WIDTH;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);//TypedArray是一个数组容器


        widthRatio = typedArray.getFloat(R.styleable.RatioLayout_width_ratio, -1);
        heightRatio = typedArray.getFloat(R.styleable.RatioLayout_height_ratio, -1);

        baseOn = typedArray.getInteger(R.styleable.RatioLayout_rl_ratio_base, BASE_ON_WIDTH);
        typedArray.recycle();

    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
                scale = dm.widthPixels / widthRatio;
            } else if (baseOn == BASE_ON_HEIGHT) {
                scale = dm.heightPixels / heightRatio;
            }

            childHeightSize = (int) (scale * heightRatio);
            childWidthSize = (int) (scale * widthRatio);

            widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    childWidthSize, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    childHeightSize, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
