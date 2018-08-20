package com.xhwbaselibrary.customview.refreshview.drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.util.Log;

import com.xhwbaselibrary.R;
import com.xhwbaselibrary.customview.refreshview.BaseRefreshView;
import com.xhwbaselibrary.customview.refreshview.PullToRefreshView;


/**
 * Created by RavenWang on 16/4/14.
 */
public class HWRefreshDrawable extends BaseRefreshView implements Animatable {

    public static final String TAG = "J_REFRESH";

    private int mTop;
    private PullToRefreshView mParent;
    private Matrix mMatrix;
    private Bitmap bitmap ;
    private Bitmap progressBitmap ;
    private float mPercent = 0.0f;
    private int bitmapHeight = 200;
    private int bitmapWidth = 200 ;
    private int progressBitmapSize = 200 ;
    private int textSize  ;
    private float textX ;
    private float textY ;

    private String text = "下拉刷新";
    private Paint paint;

    private boolean isRefreshing = false ;

    /**
     * 箭头图片距顶部的距离，单位Px
     */
    private float arrowMarginTop  ;
    /**
     * 箭头图片距左侧的距离，单位Px
     */
    private float arrowMarginLeft  ;

    private float arrowRotate ;

    private float angle = 0;


    public HWRefreshDrawable(Context context, final PullToRefreshView layout) {
        super(context, layout);
        mParent = layout ;

        layout.post(new Runnable() {
            @Override
            public void run() {
                initParams(layout.getWidth());
            }
        });

    }


    private void initParams(int viewWidth){

        if(viewWidth <= 0){
            return ;
        }

        mMatrix = new Matrix();
        mTop = -mParent.getTotalDragDistance();
        paint = new Paint();
        paint.setColor(Color.GRAY);
        textSize = dpToPixel(getContext(),16);
        paint.setTextSize(textSize);
        bitmapHeight = dpToPixel(getContext(),32);
        bitmapWidth = dpToPixel(getContext(),13);
        progressBitmapSize = dpToPixel(getContext(),25) ;
        arrowMarginTop = (dpToPixel(getContext(),80) - bitmapHeight)/2;


        //因为需要旋转，所以得给箭头留出一个方型区域，所以下面减的是bitmapHeight而不是bitmapWidth
        arrowMarginLeft = (viewWidth - bitmapHeight - textSize*4)/2;
        textX = arrowMarginLeft+bitmapHeight;
        textY = ((bitmapHeight - textSize)/2)+arrowMarginTop+textSize;

        Log.i(TAG,"-------------------------");
        Log.i(TAG,"textSize = "+textSize);
        Log.i(TAG,"bitmapHeight = "+bitmapHeight);
        Log.i(TAG,"bitmapWidth = "+bitmapWidth);
        Log.i(TAG,"arrowMarginTop = "+arrowMarginTop);
        Log.i(TAG,"textX = "+textX);
        Log.i(TAG,"textY = "+textY);
        Log.i(TAG,"-------------------------");
        initBitmap();
    }


    private void initBitmap(){
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arrow_down);
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true);


        progressBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.progress_bar);
        progressBitmap = Bitmap.createScaledBitmap(progressBitmap, progressBitmapSize, progressBitmapSize, true);
    }

    @Override
    public void setPercent(float percent, boolean invalidate) {
//        Log.i(TAG,"setPercent()---> percent = "+percent+" , invalidate = "+invalidate);
        mPercent = percent;
    }

    @Override
    public void offsetTopAndBottom(int offset) {
//        Log.i(TAG,"offsetTopAndBottom()---> offset = "+offset);
        mTop += offset;
        invalidateSelf();
    }

    @Override
    public void start() {
        Log.i(TAG,"start()");
        text = "正在刷新";
        isRefreshing = true ;

    }

    @Override
    public void stop() {
        Log.i(TAG,"stop()");

        text = "下拉刷新";
        isRefreshing = false ;
        mMatrix.reset();
        arrowRotate = 0 ;
        angle = 0;
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
        Matrix matrix = mMatrix;
        matrix.reset();

        float dragPercent = Math.min(1f, Math.abs(mPercent));
        Log.i(TAG,"drawLogo()---> dragPercent = "+dragPercent);
        float offsetY = (1.0f - dragPercent) * mParent.getTotalDragDistance();
//        matrix.postTranslate(0, offsetY);
//        canvas.drawBitmap(bitmap, matrix, null);
//        float startPx = -(bitmapHeight);
//        float newPx = (1.0f - dragPercent)*startPx+20;



        if(dragPercent <= 1.0){

            if(isRefreshing){
                text = "正在刷新";
            }else{
                text = "下拉刷新";
            }

        }

        if(!isRefreshing && dragPercent >= 1.0){
            text = "给哥放手！";
        }

        if(!isRefreshing && dragPercent > 0.7){
                matrix.setRotate(180f*(dragPercent - 0.7f)/0.3f,bitmapWidth/2,bitmapHeight/2);
        }

        if(isRefreshing){
            matrix.setRotate(angle += 10,progressBitmapSize/2,progressBitmapSize/2);
        }

        matrix.postTranslate(arrowMarginLeft, arrowMarginTop);

        canvas.drawColor(Color.YELLOW);
        if(isRefreshing){
            canvas.drawBitmap(progressBitmap, matrix,null);
        }else{
            canvas.drawBitmap(bitmap, matrix,null);
        }


//        canvas.drawBitmap(bitmap,200,newPx,null);
        canvas.drawText(text,textX,textY,paint);
    }




    public static int dpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


}
