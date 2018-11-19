package com.xuhuawei.love.fetaleducation.adapter;

import android.view.View;

import com.xhwbaselibrary.adapters.MyRecyclerBaseAdapter;
import com.xhwbaselibrary.interfaces.LifeCircleContext;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.adapter.viewholder.AskQuestionViewHolder;
import com.xuhuawei.love.fetaleducation.adapter.viewholder.MainListViewHolder;
import com.xuhuawei.love.fetaleducation.bean.StoryBean;

import java.util.List;

public class AskQuestionAdapter extends MyRecyclerBaseAdapter<AskQuestionViewHolder,StoryBean> {
    public AskQuestionAdapter(LifeCircleContext context, List<StoryBean> arrayList) {
        super(context, arrayList);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.adapter_main_ask;
    }

    @Override
    public AskQuestionViewHolder getViewHolder(View view) {
        return new AskQuestionViewHolder(this,view);
    }
}
