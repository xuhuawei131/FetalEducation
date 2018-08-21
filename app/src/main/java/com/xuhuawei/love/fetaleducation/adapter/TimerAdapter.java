package com.xuhuawei.love.fetaleducation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xuhuawei.love.fetaleducation.adapter.viewholder.TimerViewHolder;
import com.xuhuawei.love.fetaleducation.bean.TimerBean;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerViewHolder> {
    private List<TimerBean> arrayList;

    public TimerAdapter(List<TimerBean> arrayList){
        this.arrayList=arrayList;
    }

    @Override
    public TimerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  TimerViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(TimerViewHolder holder, int position) {
        holder.setData(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
