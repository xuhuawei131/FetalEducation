package com.xhwbaselibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xhwbaselibrary.adapters.viewholders.BaseViewHolder;
import com.xhwbaselibrary.interfaces.AdapterBehavior;
import com.xhwbaselibrary.interfaces.LifeCircleContext;

import java.util.List;


/**
 * Created by xuhuawei on 2017/11/22.
 * listview、GridView的适配器
 *
 */

public  abstract class MyRecyclerBaseAdapter<T extends BaseViewHolder<DataType>, DataType> extends RecyclerView.Adapter<T> implements AdapterBehavior<DataType> {
    private List<DataType> arrayList;
    private LifeCircleContext context;

    public MyRecyclerBaseAdapter(LifeCircleContext context, List<DataType> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    protected abstract int getLayoutId(int viewType);

    public abstract T getViewHolder(View view);

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutId(viewType);
        View convertView = LayoutInflater.from(context.getContext()).inflate(layoutId, parent, false);
        T holder = getViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        holder.setViewHolderData(position, arrayList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }
    //--------------------AdapterBehavior------------------------
    @Override
    public LifeCircleContext getLifeCircleContext() {
        return context;
    }

    @Override
    public List<DataType> getArrayList() {
        return arrayList;
    }
}
