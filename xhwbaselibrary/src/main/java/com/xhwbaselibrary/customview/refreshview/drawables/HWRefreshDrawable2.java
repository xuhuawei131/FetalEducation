package com.xhwbaselibrary.customview.refreshview.drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Animatable;
import android.util.Log;


import com.xhwbaselibrary.R;
import com.xhwbaselibrary.customview.refreshview.BaseRefreshView;
import com.xhwbaselibrary.customview.refreshview.PullToRefreshView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by RavenWang on 16/4/14.
 * 小鸟下拉飞翔
 */
public class HWRefreshDrawable2 extends BaseRefreshView implements Animatable {

    public static final String TAG = "REF";

    private int mTop;
    private PullToRefreshView mParent;
    private Matrix mMatrix;
    private Bitmap frame1 ;
    private Bitmap frame2 ;
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


    private boolean isRefreshing = false ;

    /**
     * 图片距顶部的距离，单位Px
     */
    private float arrowMarginTop  ;
    /**
     * 图片距左侧的距离，单位Px
     */
    private float arrowMarginLeft  ;

    /**
     * 时间控制器，用于控制两帧之间的刷新间隔
     */
    private long timestamp = 0 ;

    private boolean useFrame1 = false;


    private Subscription subscription ;

    private boolean isAnimating = false ;



    public HWRefreshDrawable2(Context context, final PullToRefreshView layout) {
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

        bitmapSize = dpToPixel(getContext(),80);
        maxDragDistance = dpToPixel(getContext(),80);
        arrowMarginTop = (maxDragDistance - bitmapSize)/2;


        //因为需要旋转，所以得给箭头留出一个方型区域，所以下面减的是bitmapHeight而不是bitmapWidth
        arrowMarginLeft = (viewWidth - bitmapSize)/2;

//        Log.i(TAG,"-------------------------");
//        Log.i(TAG,"textSize = "+textSize);
//        Log.i(TAG,"bitmapHeight = "+bitmapHeight);
//        Log.i(TAG,"bitmapWidth = "+bitmapWidth);
//        Log.i(TAG,"arrowMarginTop = "+arrowMarginTop);
//        Log.i(TAG,"textX = "+textX);
//        Log.i(TAG,"textY = "+textY);
//        Log.i(TAG,"-------------------------");
        initBitmap();
    }


    private void initBitmap(){
        frame1 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jyloading1);
        frame1 = Bitmap.createScaledBitmap(frame1, bitmapSize, bitmapSize, true);


        frame2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jyloading2);
        frame2 = Bitmap.createScaledBitmap(frame2, bitmapSize, bitmapSize, true);


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
        if(!isAnimating){
            startAnimation();
        }

//
    }

    @Override
    public void start() {
        Log.i(TAG,"start()");
        isRefreshing = true ;

    }

    @Override
    public void stop() {
        Log.i(TAG, "stop()");
        isRefreshing = false ;
        mMatrix.reset();
        timestamp = 0 ;
        if(!subscription.isUnsubscribed()){
            subscription.unsubscribe();
            subscription = null ;
        }
        Observable.just("xx")
                .subscribeOn(Schedulers.io())
                .delay(200,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        isAnimating = false ;
                    }
                });



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

        if(!isRefreshing){
            float scale = (1 - origenalScale)*dragPercent+origenalScale;
            matrix.setScale(scale,scale);
        }

        if(isRefreshing){
            matrix.setScale(1.0f,1.0f);
        }

        matrix.postTranslate(arrowMarginLeft, arrowMarginTop);

        canvas.drawRGB(240,240,240);



        canvas.drawBitmap(getFrame(), matrix,null);

    }

    private Bitmap getFrame(){
        long time = System.currentTimeMillis();
        if(timestamp == 0){
            timestamp = time ;
            useFrame1 = true ;
            return frame1;
        }

        if(useFrame1){
            if((time - timestamp) > 100){
                timestamp = time ;
                useFrame1 = false ;
                return frame2;
            }else{
                return frame1;
            }


        }else{

            if((time - timestamp) > 100){
                timestamp = time ;
                useFrame1 = true ;
                return frame1;
            }else{
                return frame2;
            }

        }

    }




    public static int dpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


    private void startAnimation(){

        if(subscription != null && !subscription.isUnsubscribed()){
            return ;
        }

        isAnimating = true ;

        subscription = Observable.just("aaa")
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        invalidateSelf();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .delay(20, TimeUnit.MILLISECONDS)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        invalidateSelf();
                    }
                });

    }


}
