package com.xhwbaselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;
import com.xhwbaselibrary.interfaces.LifeCircleContext;
import com.xhwbaselibrary.lifecircle.LifeCircleCallback;
import com.xhwbaselibrary.lifecircle.Lifecycle;
import com.xhwbaselibrary.lifecircle.LifecycleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by lingdian on 17/9/13.
 */

public abstract class BaseActivity extends BasePermissActivity implements LifeCircleContext,LifeCircleCallback {
    private LifecycleManager lifecycleManager = new LifecycleManager();

    protected abstract void init();

    protected abstract int setContentView();

    protected abstract void findViewByIds();

    protected abstract void requestService();

    protected abstract void onMyDestory();

    private CatLoadingView mView;
    private List<Toast> arrayList;
    private Subscription initObservable;


    public < T extends View> T findMyViewById(@IdRes int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        arrayList = new ArrayList<>();
        int resLayout = setContentView();
        setContentView(resLayout);
        findViewByIds();
        requestService();
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
                        requestService();
                    }
                });
        lifecycleManager.onCreate();
    }

    public void showProgressDialog(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "加载中...";
        }
        if (mView == null) {
            mView = new CatLoadingView();
        }
        if (!mView.isAdded()) {
            mView.show(getSupportFragmentManager(), content);
        }
    }

    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public void disProgressDialog() {
        if (mView != null && mView.isAdded()) {
            mView.dismiss();
        }
    }

    public void showToast(String content) {
        Toast toast = Toast.makeText(getContext(), content, Toast.LENGTH_SHORT);
        toast.show();
        arrayList.add(toast);
    }

    public void showToast(int content) {
        Toast toast = Toast.makeText(getContext(), content, Toast.LENGTH_SHORT);
        toast.show();
        arrayList.add(toast);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleManager.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycleManager.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Toast toast : arrayList) {
            toast.cancel();
        }
        if (initObservable != null && !initObservable.isUnsubscribed()) {
            initObservable.unsubscribe();
        }

        arrayList.clear();
        lifecycleManager.onDestroy();
        lifecycleManager = null;
        onMyDestory();
    }

    @Override
    public void addLifecycleListener(@NonNull Lifecycle lifecycle) {
        lifecycleManager.add(lifecycle);
    }

    @Override
    public void removeLifecycleListener(@NonNull Lifecycle lifecycle) {
        lifecycleManager.remove(lifecycle);
    }

    public void removeLifecycleListener(@NonNull String listenerId) {
        lifecycleManager.remove(listenerId);
    }
}
