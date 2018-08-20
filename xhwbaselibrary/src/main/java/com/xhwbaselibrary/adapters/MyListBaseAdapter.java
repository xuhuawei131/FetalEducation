package com.xhwbaselibrary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xhwbaselibrary.adapters.viewholders.BaseViewHolder;
import com.xhwbaselibrary.interfaces.AdapterBehavior;
import com.xhwbaselibrary.interfaces.LifeCircleContext;

import java.util.List;


/**
 * Created by xuhuawei on 2017/11/22.
 * listview、GridView的适配器
 *
 */

public abstract class MyListBaseAdapter<T extends BaseViewHolder<DataType>, DataType> extends BaseAdapter implements AdapterBehavior<DataType> {
    private List<DataType> arrayList;
    private LifeCircleContext context;

    public MyListBaseAdapter(LifeCircleContext context, List<DataType> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    protected abstract int getLayoutId(int viewType);

    public abstract T getViewHolder(View view);

    @Override
    public int getCount() {
        int length=arrayList == null ? 0 : arrayList.size();
        return length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T holder;
        if (convertView == null) {
            int viewType = getItemViewType(position);
            int layoutId = getLayoutId(viewType);

            convertView = LayoutInflater.from(context.getContext()).inflate(layoutId, parent, false);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (T) convertView.getTag();
        }
        holder.setViewHolderData(position, arrayList.get(position));
        return convertView;
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
