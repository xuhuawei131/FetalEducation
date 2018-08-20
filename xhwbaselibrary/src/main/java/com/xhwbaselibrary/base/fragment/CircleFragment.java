package com.xhwbaselibrary.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.xhwbaselibrary.utils.UIUtils;

/**
 * Created by lingdian on 2018/3/19.
 */

public class CircleFragment extends Fragment {
    //是否初始化成功
    private boolean mInited = false;
    private boolean mFinishing = false;

    //入场动画类型
    private AnimType mAnimType = AnimType.RIGHT_TO_LEFT;
    /*
    * 设置入场动画类型
    */
    public void setAnimationType(AnimType type){
        this.mAnimType = type;
    }

    //是否对用户可见
    private boolean mVisible = false;

    /*
    * 初始化新的Fragment
    */
    public static <T extends CircleFragment> T newFragment(Context context, Class<?> cls) {
        T fragment = (T) Fragment.instantiate(context, cls.getName());
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setVisibleToUser(isVisibleToUser);
    }

    public void setVisibleToUser(boolean visible) {
        if (visible == mVisible) {
            return;
        }
        this.mVisible = visible;
    }

    public boolean isFinishing() {
        return mFinishing;
    }
    protected void setFinishing() {
        this.mFinishing = true;
    }

    //============================ UI导航部分 ===================================================

    /*
     * 打开从右向左的Fragment
     */
    public void showPushFragment(CircleFragment fragment){
        fragment.setAnimationType(AnimType.RIGHT_TO_LEFT);
        showFragment(fragment);
    }

    /*
     * 打开弹出Fragment
     */
    public void showPopFragment(CircleFragment fragment){
        fragment.setAnimationType(AnimType.BOTTOM_TO_TOP);
        showFragment(fragment);
    }

    public void showFragment(CircleFragment fragment) {
//        super.showFragment(fragment);
    }

    public <R extends CircleFragment> R showFragment(Class<R> clazz, Bundle bundle) {
        CircleFragment fragment = newFragment(getActivity(), clazz);
        fragment.setArguments(bundle);
        showFragment(fragment);
        return (R) fragment;
    }


    @Override
    public Animation onCreateAnimation(int transit, final boolean enter, int nextAnim) {
        Animation animation = null;
        if (enter) {
            animation = getAnimationIn();
        }

        if(animation == null)
            return null;

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
//                if (enter) {
//                    BaseUIFragment.this.onPanelOpened(null);
//                } else {
//                    BaseUIFragment.this.onPanelClosed(null);
//                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        return animation;
    }


    public void finish() {
        if (isFinishing())
            return;

        setFinishing();
        UIUtils.hideInputMethod(getActivity());

        Animation animation = getAnimationOut();
        if (animation != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    BaseUIFragment.this.onPanelClosed(null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            getView().startAnimation(animation);
        } else {
//            if (!isSlideable()) {
//                onPanelClosed(null);
//            }
//            super.finish();
        }
    }

    /*
        * 获得入场动画
        */
    protected Animation getAnimationIn(){
        if (mAnimType == AnimType.RIGHT_TO_LEFT) {
            Animation animation = new TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_SELF, 1.0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0,
                    TranslateAnimation.ABSOLUTE, 0,
                    TranslateAnimation.ABSOLUTE, 0);
            animation.setDuration(200);
            animation.setInterpolator(new AccelerateInterpolator());
            return animation;
        } else if(mAnimType == AnimType.BOTTOM_TO_TOP){
            Animation animation = new TranslateAnimation(
                    TranslateAnimation.ABSOLUTE, 0,
                    TranslateAnimation.ABSOLUTE, 0,
                    TranslateAnimation.RELATIVE_TO_SELF, 1.0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0);
            animation.setDuration(200);
            animation.setInterpolator(new AccelerateInterpolator());
            return animation;
        }
        return null;
    }

    /*
     * 获得出场动画
     */
    protected Animation getAnimationOut(){
        //对用户不可见，则不执行出场动画
        if (!mVisible || !isVisible()) {
            return null;
        }
        if (mAnimType == AnimType.BOTTOM_TO_TOP) {
            Animation animation = new TranslateAnimation(
                    TranslateAnimation.ABSOLUTE, 0,
                    TranslateAnimation.ABSOLUTE, 0,
                    TranslateAnimation.RELATIVE_TO_SELF, 0,
                    TranslateAnimation.RELATIVE_TO_SELF, 1.0f);
            animation.setDuration(200);
            animation.setInterpolator(new AccelerateInterpolator());
            return animation;
        }
        return null;
    }
}
