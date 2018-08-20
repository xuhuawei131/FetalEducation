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
public class HWRefreshDrawable4 extends BaseRefreshView implements Animatable {

    public static final String TAG = "REF";

    private int mTop;
    private PullToRefreshView mParent;
    private Matrix mMatrix;
    private static Bitmap frame1 ;
    private static Bitmap frame2 ;
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


//    private Subscription subscription ;

    private boolean isAnimating = false ;

    private Paint paint ;
    private int textSize ;
    private String content = "下拉刷新";

    private Thread drawThread = null ;
    private long interval = 100 ;
    private int background = Color.parseColor("#F0F0F0");



    public HWRefreshDrawable4(Context context, final PullToRefreshView layout) {
        super(context, layout);
        mParent = layout ;
        mMatrix = new Matrix();
        bitmapSize = dpToPixel(getContext(),60);
        maxDragDistance = dpToPixel(getContext(),80);
        arrowMarginTop = (maxDragDistance - bitmapSize)/2;
        textSize = dpToPixel(getContext(),14);
        paint = new Paint();
        paint.setColor(Color.GRAY);
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
        arrowMarginLeft = (viewWidth - bitmapSize - textSize*4)/2 - bitmapSize/3;


//        Log.i(TAG,"-------------------------");
//        Log.i(TAG,"textSize = "+textSize);
//        Log.i(TAG,"bitmapHeight = "+bitmapHeight);
//        Log.i(TAG,"bitmapWidth = "+bitmapWidth);
//        Log.i(TAG,"arrowMarginTop = "+arrowMarginTop);
//        Log.i(TAG,"textX = "+textX);
//        Log.i(TAG,"textY = "+textY);
//        Log.i(TAG,"-------------------------");

    }


    private void initBitmap(){
        if(frame1 == null){
            frame1 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jyloading1);
            frame1 = Bitmap.createScaledBitmap(frame1, bitmapSize, bitmapSize, true);
        }


        if(frame2 == null){
            frame2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.jyloading2);
            frame2 = Bitmap.createScaledBitmap(frame2, bitmapSize, bitmapSize, true);
        }



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
        content = "正在刷新";
        isRefreshing = true ;

    }

    @Override
    public void stop() {
        Log.i(TAG, "stop()");
        isRefreshing = false ;
        mMatrix.reset();
        timestamp = 0 ;
        content = "下拉刷新";
        stopDraw();



    }


    private synchronized void stopDraw(){

        isAnimating = false ;

//        if(subscription != null && !subscription.isUnsubscribed()){
//            subscription.unsubscribe();
//            subscription = null ;
//        }
//        Observable.just("xx")
//                .subscribeOn(Schedulers.io())
//                .delay(100,TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        isAnimating = false;
//                    }
//                });
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

        float scale = (1 - origenalScale)*dragPercent+origenalScale;
        float scaleSize = bitmapSize*scale ;
        matrix.setScale(scale,scale,bitmapSize/2,scaleSize/2);

//        if(isRefreshing){
//            matrix.setScale(1.0f,1.0f);
//        }



        matrix.postTranslate(arrowMarginLeft, (maxDragDistance - scaleSize) / 2);

//        canvas.drawRGB(240, 240, 240);
        canvas.drawColor(background);
        if(isRefreshing){
            canvas.drawBitmap(getFrame(), matrix,null);
        }else{
            if(dragPercent >= 1.0){
                content = "松开刷新";
                canvas.drawBitmap(getFrame(), matrix,null);
            }else{
                canvas.drawBitmap(frame1, matrix,null);
            }

        }

        canvas.drawText(content,arrowMarginLeft+bitmapSize+textSize/2,maxDragDistance/2+textSize/2,paint);

    }

    private Bitmap getFrame(){
        long time = System.currentTimeMillis();
        if(timestamp == 0){
            timestamp = time ;
            useFrame1 = true ;
            return frame1;
        }

        if(useFrame1){
            if((time - timestamp) > interval){
                timestamp = time ;
                useFrame1 = false ;
                return frame2;
            }else{
                return frame1;
            }


        }else{

            if((time - timestamp) > interval){
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


    private synchronized void startAnimation(){

//        if(subscription != null && !subscription.isUnsubscribed()){
//            return ;
//        }

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

//        subscription = Observable.just("aaa")
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        invalidateSelf();
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.io())
//                .delay(20, TimeUnit.MILLISECONDS)
//                .repeat()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        invalidateSelf();
//                    }
//                });

    }


}
