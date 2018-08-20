package com.xhwbaselibrary.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xhwbaselibrary.interfaces.LifeCircleContext;
import com.xhwbaselibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lingdian on 2018/3/19.
 */

public abstract class BaseFragment extends Fragment implements LifeCircleContext {
    /**
     * 初始化的工作都放在这里
     *
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 获取控件的布局
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化控件放在这里
     * 以及设置各种Listener
     */
    protected abstract void findViewByIds();

    /**
     * 注册广播等
     * 一些讲究 尽快注册的工作可以放在这里
     * 如果不需要尽快注册的 尽可能的放在requestService中做
     */
    protected abstract void registerMoudles();

    /**
     * 做一些设置数据 或者请求网络工作
     * 为了防止Fragment加载时候卡顿
     * 延迟了一段时间区调用
     */
    protected abstract void requestService();

    /**
     * 一些回收的工作都在这里
     */
    protected abstract void onMyDestory();

    private Subscription initObservable;
    private List<Toast> cacheToastList;

    private int requestId=-1;
    private BaseFragment lastFragment;

    protected View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cacheToastList = new ArrayList<>();
        Bundle bundle=getArguments();
        if(bundle!=null){
            requestId=bundle.getInt("requestId",-1);
        }

        init(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        view= View.inflate(getActivity(), getContentViewId(), null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewByIds();
        registerMoudles();
        initObservable = Observable.just("").subscribeOn(Schedulers.io())
                .delay(500, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (!isDetached() && getActivity() != null) {
                            requestService();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (initObservable != null && !initObservable.isUnsubscribed()) {
            initObservable.unsubscribe();
        }
        for (Toast toast : cacheToastList) {
            toast.cancel();
        }
        cacheToastList.clear();
        onMyDestory();

    }

    /**
     * findview工作可以使用他
     *
     * @param resId
     * @return View
     */
    protected View findViewById(int resId) {
        return view.findViewById(resId);
    }

    /**
     * 对每一个要显示Toast做了工作
     * 如果页面关闭 会立即回收 防止页面关闭了 还在显示toast
     * 对于多次点击toast的回收 很有效果
     *
     * @param res
     */
    protected void showToast(String res) {
        Toast toast = Toast.makeText(getContext(), res, Toast.LENGTH_SHORT);
        toast.show();
        cacheToastList.add(toast);

    }

    protected void showToast(int res) {
        showToast(getString(res));
    }

    /**
     * 显示toast
     * @param res
     * @param isOk
     */
    protected void showToast(String res, boolean isOk) {
        Toast toast = ToastUtil.showActionResult(res, isOk);
        cacheToastList.add(toast);
    }

    protected void showToast(int res, boolean isOk) {
        Toast toast = ToastUtil.showActionResult(res, isOk);
        cacheToastList.add(toast);
    }

    protected void showPushFragmentForResult(BaseFragment fragment, int request){
//        fragment.setAnimationType(AnimType.RIGHT_TO_LEFT);
        showFragmentForResult(fragment,request);
    }
    protected void showPopFragmentForResult(BaseFragment fragment,int request){
//        fragment.setAnimationType(AnimType.BOTTOM_TO_TOP);
        showFragmentForResult(fragment,request);
    }
    protected void showFragmentForResult(BaseFragment fragment,int requestId){
        Bundle bundle=fragment.getArguments();
        if (bundle==null){
            bundle=new Bundle();
            bundle.putInt("requestId",requestId);
        }
        fragment.setArguments(bundle);

//        if (fragment instanceof MyBaseUIFragment){
//            ((BaseFragment)fragment).lastFragment=this;
//        }
//        super.showFragment(fragment);
    }

    protected void OnFragmentResult(Bundle bundle,int request,int result){

    }

    public void setResult(Bundle bundle,int result){
        if (bundle==null){
            bundle=new Bundle();
        }
        if (lastFragment!=null){
            lastFragment.OnFragmentResult(bundle,requestId,result);
        }
    }
}
