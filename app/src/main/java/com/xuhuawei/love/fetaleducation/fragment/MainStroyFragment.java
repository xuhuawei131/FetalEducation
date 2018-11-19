package com.xuhuawei.love.fetaleducation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xhwbaselibrary.base.BaseRefreshMoreViewActivity;
import com.xhwbaselibrary.base.fragment.BaseRefreshMoreViewFragment;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.adapter.HomePageAdapter;
import com.xuhuawei.love.fetaleducation.bean.StoryBean;
import com.xuhuawei.love.fetaleducation.parsers.ParserStringCallBack;
import com.xuhuawei.love.fetaleducation.utils.HtmlPageUrlUtils;
import com.xuhuawei.love.fetaleducation.utils.HtmlParer;

import java.util.ArrayList;
import java.util.List;

public class MainStroyFragment extends BaseRefreshMoreViewFragment {
    private List<StoryBean> arrayList;
    private int currentIndex = 0;
    private boolean isDoingRequest = false;

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    protected int getJRefreshLayoutId() {
        return R.id.refreshLayout;
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.recyclerView;
    }

    @Override
    protected void findRefreshMoreViewByIds() {
        arrayList = new ArrayList<>();
        HomePageAdapter adapter = new HomePageAdapter(this, arrayList);
        setAdapter(adapter);
    }

    @Override
    protected void registerMoudles() {

    }

    @Override
    protected void requestService() {
        setAutoRefreshView();
    }

    @Override
    protected BaseRefreshMoreViewActivity.RefreshReturnType doStartTask() {
        return BaseRefreshMoreViewActivity.RefreshReturnType.UI_THREAD;
    }

    @Override
    protected Object doRefreshTask() {
        currentIndex=0;
        requestNetData(currentIndex+1);
        return null;
    }

    @Override
    protected void doRefreshEndingTask(Object o) {
    }

    @Override
    protected void onMoreTask() {
        requestNetData(currentIndex + 1);
    }

    @Override
    protected void onMyDestory() {

    }

    /**
     * 空提醒
     */
    private void notifyEmptyAdapter() {
        int length = arrayList.size();
        if (length == 0) {
            findViewById(R.id.textEmptyView).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.textEmptyView).setVisibility(View.GONE);
        }
    }

    private void requestNetData(final int index) {
        if (isDoingRequest) {
            return;
        }
        isDoingRequest = true;
        OkGo.<String>get(HtmlPageUrlUtils.getPageUrlByIndex(index))
                .headers("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)")
                .tag(this).execute(new ParserStringCallBack<List<StoryBean>>() {

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                notifyEmptyAdapter();
            }

            @Override
            public List<StoryBean> parserJson(Response<String> response) {
                List<StoryBean> dataList = HtmlParer.dealArticleListResult(response);
                return dataList;
            }

            @Override
            public void onResultComing(List<StoryBean> response) {
                currentIndex = index;
                isDoingRequest = false;
                if (index == 1) {
                    setRefreshFinish();
                    arrayList.clear();
                    arrayList.addAll(response);
                    notifyEmptyAdapter();
                } else {
                    showNormalFootView();
                    arrayList.addAll(response);
                }
                notifyDataSetChanged();
            }
        });
    }
}
