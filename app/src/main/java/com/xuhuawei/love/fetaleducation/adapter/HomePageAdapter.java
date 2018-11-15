package com.xuhuawei.love.fetaleducation.adapter;

import android.view.View;

import com.xhwbaselibrary.adapters.MyRecyclerBaseAdapter;
import com.xhwbaselibrary.interfaces.LifeCircleContext;
import com.xuhuawei.love.fetaleducation.adapter.viewholder.MainListViewHolder;
import com.xuhuawei.love.fetaleducation.bean.StoryBean;

import java.util.List;

public class HomePageAdapter extends MyRecyclerBaseAdapter<MainListViewHolder,StoryBean> {
    public HomePageAdapter(LifeCircleContext context, List<StoryBean> arrayList) {
        super(context, arrayList);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    public MainListViewHolder getViewHolder(View view) {
        return null;
    }
}
