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
 */

public abstract class MyMutiRecyclerBaseAdapter<DataType> extends RecyclerView.Adapter<BaseViewHolder> implements AdapterBehavior<DataType> {
    private List<DataType> arrayList;
    private LifeCircleContext context;

    public MyMutiRecyclerBaseAdapter(LifeCircleContext context, List<DataType> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    /**
     * 通过position获取layout类型
     *
     * @param position
     * @return 返回值 从0依次递增
     */
    protected abstract int getLayoutType(int position);


    /**
     * 获取对应的ViewHolder
     *
     * @param viewType
     * @param view
     * @return
     */
    public abstract BaseViewHolder getViewHolder(int viewType, ViewGroup view);


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        int count = arrayList == null ? 0 : arrayList.size();
        return count;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        holder = getViewHolder(viewType, parent);
        return holder;
    }

    protected View getInfalteView(int layoutId,ViewGroup parent){
        return LayoutInflater.from(context.getContext()).inflate(layoutId, parent, false);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setViewHolderData(position,arrayList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutType(position);
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
