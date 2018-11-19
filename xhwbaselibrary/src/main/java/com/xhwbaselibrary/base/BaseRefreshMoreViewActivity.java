package com.xhwbaselibrary.base;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.xhwbaselibrary.customview.autorefresh.AutoHWRefreshLayout;
import com.xhwbaselibrary.customview.autorefresh.AutoPullToRefreshView;
import com.xhwbaselibrary.customview.itemdecoration.ItemDecoration;
import com.xhwbaselibrary.customview.moreview.EndlessRecyclerOnScrollListener;
import com.xhwbaselibrary.customview.moreview.HeaderAndFooterRecyclerViewAdapter;
import com.xhwbaselibrary.customview.moreview.LoadingFooter;
import com.xhwbaselibrary.customview.moreview.RecyclerViewStateUtils;
import com.xhwbaselibrary.customview.refreshview.HWRefreshLayout;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lingdian on 17/9/13.
 * 下拉刷新以及更多的基类
 */

public abstract class BaseRefreshMoreViewActivity extends BaseActivity   {
    protected AutoHWRefreshLayout refresh_layout;
    private static final int MAX_PAGE_NUM=20;
    protected abstract int getJRefreshLayoutId();

    protected abstract void findRefreshMoreViewByIds();


    /**
     * 这里是拉去最新数据的时候 之前执行的操作
     * 返回值 决定了 doLongTimeTask执行在哪个线程里面
     * @return NONE：不刷新，刷新之后立即关闭 SUB_THREAD：子线程 UI_THREAD：主线程
     */
    protected abstract RefreshReturnType doStartTask();
    /**
     * 开始执行刷新的任务
     * @return
     */
    protected abstract Object doRefreshTask();
    /**
     * 获取完 数据之后 的操作
     * @param o
     */
    protected abstract void doRefreshEndingTask(Object o);

    public enum RefreshReturnType {//不刷新，子线程，ui线程
        NONE, SUB_THREAD, UI_THREAD
    }

    //---------------------------------------
    protected RecyclerView mListView;
    protected boolean hasMore = true;

    /**
     * 执行获取更多数据的操作
     * 如果执行完之后 没有数据了 那么 设置 hasMore 为false
     *
     */
    protected abstract void onMoreTask();

    protected abstract int getRecyclerViewId();

    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    @Override
    protected final void findViewByIds() {
        refresh_layout = (AutoHWRefreshLayout) findViewById(getJRefreshLayoutId());
        if (refresh_layout != null) {
            refresh_layout.setOnRefreshListener(onRefreshListener);
        }

        mListView = (RecyclerView) findViewById(getRecyclerViewId());
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setBackgroundColor(Color.WHITE);
        mListView.addOnScrollListener(scrollListener);
//        ItemDecoration decoration = new ItemDecoration(this, LinearLayoutManager.VERTICAL);
//        mListView.addItemDecoration(decoration);

        findRefreshMoreViewByIds();
    }

    /**
     * 设置自动刷新
     */
    protected void setAutoRefreshView() {
        if (refresh_layout != null && !refresh_layout.isRefreshing()) {
            refresh_layout.post(new Runnable() {
                @Override
                public void run() {
                    refresh_layout.setAutoRefreshView();
                }
            });
        }
    }

    /**
     * 关闭刷新的状态
     */
    protected void setRefreshFinish() {
        if (refresh_layout!=null&&refresh_layout.isRefreshing()) {
            refresh_layout.setRefreshing(false);
        }
    }



    private AutoPullToRefreshView.OnRefreshListener onRefreshListener=new AutoPullToRefreshView.OnRefreshListener(){
        @Override
        public void onRefresh() {
            if (refresh_layout != null && !refresh_layout.isRefreshing()) {
                refresh_layout.setRefreshing(true);
            }
            RefreshReturnType type = doStartTask();
            if (type == RefreshReturnType.NONE) {
                refresh_layout.setRefreshing(false);
            } else if (type == RefreshReturnType.UI_THREAD||type==null) {
                doRefreshTask();
            } else {
                Observable.just("").subscribeOn(Schedulers.io())
                        .map(new Func1<String, Object>() {
                            @Override
                            public Object call(String s) {
                                return doRefreshTask();
                            }
                        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        setRefreshFinish();
                        doRefreshEndingTask(o);
                    }
                });
            }
        }
    };




    private EndlessRecyclerOnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mListView);
            if (state == LoadingFooter.State.Loading) {
                return;
            }
            if (hasMore) {
//                showLoadingFootView(getMaxNumByPage());
                showNormalFootView();
                onMoreTask();
            } else {
                //没有更多数据
                showEndingFootView();
            }
        }
    };

    protected void showNormalFootView() {
        RecyclerViewStateUtils.setFooterViewState(this.mListView, LoadingFooter.State.Normal);
    }

    protected void showLoadingFootView(int maxLength) {
        RecyclerViewStateUtils.setFooterViewStateNoScroll(this, mListView, maxLength, LoadingFooter.State.Loading, null);
    }

    protected void showLoadingFootView() {
        showLoadingFootView(MAX_PAGE_NUM);
    }

    protected void showEndingFootView() {
        showEndingFootView(MAX_PAGE_NUM);
    }

    protected void showEndingFootView(int maxLength) {
        RecyclerViewStateUtils.setFooterViewStateNoScroll(this, mListView, maxLength, LoadingFooter.State.TheEnd, null);
    }

    protected void showErrorFootView() {
        showErrorFootView(MAX_PAGE_NUM);
    }

    protected void showErrorFootView(int maxLength) {
        RecyclerViewStateUtils.setFooterViewStateNoScroll(this, mListView, maxLength, LoadingFooter.State.NetWorkError, null);
    }

    protected int getMaxNumByPage() {
        return MAX_PAGE_NUM;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        mListView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }

    public void notifyDataSetChanged(){
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

}
