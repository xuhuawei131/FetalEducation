package com.xuhuawei.love.fetaleducation.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.xhwbaselibrary.adapters.viewholders.BaseViewHolder;
import com.xhwbaselibrary.interfaces.AdapterBehavior;

import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.bean.MainBean;

public class MainViewHolder extends BaseViewHolder<MainBean> {

    private View view_selected;
    private TextView text_time;
    private TextView text_name;

    public MainViewHolder(AdapterBehavior behavior, View itemView) {
        super(behavior, itemView);
    }

    @Override
    protected void findViewByIds() {
        view_selected=findViewById(R.id.view_selected);
        text_time=findViewById(R.id.text_time);
        text_name=findViewById(R.id.text_title);
    }

    @Override
    protected void onBindView(int position, MainBean bean) {
        if (bean.isSelect){
            view_selected.setVisibility(View.VISIBLE);
        }else{
            view_selected.setVisibility(View.GONE);
        }
        text_time.setText(bean.updateTime);
        text_name.setText(bean.title);

    }
}
