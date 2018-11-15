package com.xhwbaselibrary.customview.autorefresh.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;

import com.xhwbaselibrary.customview.autorefresh.AutoBaseRefreshView;
import com.xhwbaselibrary.customview.autorefresh.AutoPullToRefreshView;


/**
 * Created by xuhuawei on 18/9/2.
 */
public class HWRefreshDrawable5 extends AutoBaseRefreshView implements Animatable {

    public static final String TAG = "REF";

    private int mTop;
    private AutoPullToRefreshView mParent;
    private Matrix mMatrix;

    private Bitmap frame[]=new Bitmap[17];
    private static final int TOTAL_SUM= 100;
    private float mPercent = 0.0f;
    /**
     * 最大下拉距离
     */
    private int maxDragDistance ;

    /**
     * 图片初始缩放比例
     */
    private float origenalScale = 0.03f ;

    private int bitmapSize ;
    private int bitmapSizeWidth ;


    private boolean isRefreshing = false ;

    /**
     * 图片距顶部的距离，单位Px
     */
    private float arrowMarginTop  ;
    /**
     * 图片距左侧的距离，单位Px
     */
    private float arrowImageMarginLeft;


    /**
     * 图片距左侧的距离，单位Px
     */
    private float arrowTextMarginLeft;

    /**
     * 图片距左侧的距离，单位Px
     */
    private float arrowTextMarginBottom;


    private int currentIndex=0;
    /**
     * 时间控制器，用于控制两帧之间的刷新间隔
     */
    private long timestamp = 0 ;
    private boolean isAnimating = false ;
    private Paint paint ;
    private int textSize ;
    private String content = "下拉刷新";

    private Thread drawThread = null ;
    private long interval = 50 ;
    private int background = Color.parseColor("#F0F0F0");

    public HWRefreshDrawable5(Context context, final AutoPullToRefreshView layout) {
        super(context, layout);
        mParent = layout ;
        mMatrix = new Matrix();
        bitmapSize = dpToPixel(getContext(),40);
        bitmapSizeWidth = dpToPixel(getContext(),16);
        maxDragDistance = dpToPixel(getContext(),90);
        arrowTextMarginBottom = dpToPixel(getContext(),10);
        arrowMarginTop = (maxDragDistance - bitmapSize)/2;

        textSize = dpToPixel(getContext(),11);
        paint = new Paint();
        paint.setColor(0xff916e4f);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);

        initBitmap();
        layout.post(new Runnable() {
            @Override
            public void run() {
                initParams(layout.getWidth());
            }
        });

    }

    public void setPaintColor(int color){
        if(paint != null){
            paint.setColor(color);
        }
    }

    public void setRefreshBackground(int color){
        this.background = color ;
    }


    private void initParams(int viewWidth){
        if(viewWidth <= 0){
            return ;
        }
        mTop = -mParent.getTotalDragDistance();
        arrowImageMarginLeft = (viewWidth - bitmapSizeWidth)/2;
    }


    private void initBitmap(){
        for (int i = 0; i <frame.length ; i++) {
            int resId =getContext().getResources().getIdentifier("a"+(i+1), "drawable", getContext().getPackageName());
            Bitmap frame1 = BitmapFactory.decodeResource(getContext().getResources(), resId);
            frame[i]=frame1;
        }
        bitmapSizeWidth= frame[frame.length-1].getWidth();
        bitmapSize= frame[frame.length-1].getHeight();
    }

    @Override
    public void setPercent(float percent, boolean invalidate) {
        mPercent = percent;
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mTop += offset;
        if(!isAnimating){
            startAnimation();
        }
    }

    @Override
    public void start() {
        content = "正在刷新";
        isRefreshing = true ;
    }

    @Override
    public void stop() {
        isRefreshing = false ;
        mMatrix.reset();
        timestamp = 0 ;
        content = "下拉刷新";
        stopDraw();
    }


    private synchronized void stopDraw(){
        isAnimating = false ;
    }
    @Override
    public boolean isRunning() {
        return false;
    }
    @Override
    public void draw(Canvas canvas) {
        final int saveCount = canvas.save();
        canvas.translate(0, mTop);
        drawLogo(canvas);
        canvas.restoreToCount(saveCount);
    }

    private void drawLogo(Canvas canvas) {
        float dragPercent = Math.min(1f, Math.abs(mPercent));
        if(isAnimating && dragPercent == 0.0){
            stopDraw();
        }
        if(dragPercent <= 1.0){
            if(isRefreshing){
                content = "正在刷新";
            }else{
                content = "下拉刷新";
            }
        }

        Matrix matrix = mMatrix;
        matrix.reset();

        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        int width = rect.width();//文本的宽度
        int height = rect.bottom+rect.height();//文本的高度

        canvas.drawColor(background);

        float bitmapY=maxDragDistance-arrowTextMarginBottom-height-bitmapSize;
//        LogUtil.v("xhw","drawLogo height="+height+", width= "+width+",measureWidth="+paint.measureText(content));
        matrix.postTranslate(arrowImageMarginLeft, dpToPixel(getContext(),12));
        canvas.drawBitmap(getFrame(), matrix,null);

        if(!isRefreshing){
            if(dragPercent >= 1.0){
                content = "松开刷新";
            }
        }
        arrowTextMarginLeft=(mParent.getWidth() - paint.measureText(content))/2;
        canvas.drawText(content, arrowTextMarginLeft,maxDragDistance-arrowTextMarginBottom-height+10,paint);
    }

    private Bitmap getFrame(){
        if (!isRefreshing){
            float progress=maxDragDistance+mTop*1.0f;
            float percent=progress/maxDragDistance;
            int index= (int) (TOTAL_SUM*percent);
            return frame[index%16];
        }else{
            long time = System.currentTimeMillis();
            if (time-timestamp>interval){
                timestamp=time;
                currentIndex++;
                if (currentIndex>15){
                    currentIndex=0;
                }
                return frame[currentIndex];
            }
            return frame[currentIndex%16];
        }
    }

    public static int dpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private synchronized void startAnimation(){
        if(isAnimating){
            return ;
        }
        isAnimating = true ;
        drawThread = new Thread(){
            @Override
            public void run() {
                while (isAnimating){
                    try {
                        mParent.post(new Runnable() {
                            @Override
                            public void run() {
                                invalidateSelf();
                            }
                        });
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        drawThread.start();
    }


}
