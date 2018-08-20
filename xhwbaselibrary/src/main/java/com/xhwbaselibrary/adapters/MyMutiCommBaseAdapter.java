package com.xhwbaselibrary.adapters;

import android.util.Log;
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

public abstract class MyMutiCommBaseAdapter<DataType > extends BaseAdapter implements AdapterBehavior<DataType> {
    private List<DataType> arrayList;
    private LifeCircleContext context;

    public MyMutiCommBaseAdapter(LifeCircleContext context, List<DataType> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    /**
     * 通过position获取layout类型
     * @param position
     * @return
     */
    protected abstract int getLayoutType(int position);

    /**
     * 获取多种类型的数目
     * @return
     */
    protected abstract int getLayoutCount();

    /**
     * 获取layout 的id
     * @param viewType
     * @return
     */
    protected abstract int getLayoutId(int viewType);

    /**
     * 获取对应的ViewHolder
     * @param viewType
     * @param view
     * @return
     */
    public abstract BaseViewHolder getViewHolder(int viewType, View view);

    @Override
    public int getCount() {
        int count=arrayList == null ? 0 : arrayList.size();
        return count;
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
    public int getViewTypeCount() {
        return getLayoutCount();
    }
    @Override
    public int getItemViewType(int position) {
        return getLayoutType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;

        if (convertView == null) {
            int viewType = getItemViewType(position);
            int layoutId = getLayoutId(viewType);
            convertView = LayoutInflater.from(context.getContext()).inflate(layoutId, parent, false);
            holder = getViewHolder(viewType,convertView);
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("xhw","convertView");
            }
        });
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
