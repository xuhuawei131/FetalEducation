package com.xhwbaselibrary.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xhwbaselibrary.interfaces.AdapterBehavior;


/**
 * Created by xuhuawei on 2017/11/22.
 */

public abstract class BaseViewHolder<DataType> extends  RecyclerView.ViewHolder{
    protected int position;
    protected DataType bean;
    protected AdapterBehavior behavior;
    /**
     * 仅仅做数据初始化的工作
     */
    protected abstract void findViewByIds();

    /**
     * 设置数据
     * 设置监听器 都是在这里
     * @param position
     * @param bean
     */
    protected abstract void onBindView(int position, DataType bean);

    public BaseViewHolder(AdapterBehavior behavior, View itemView) {
        super(itemView);
        this.behavior=behavior;
        findViewByIds();
    }
    public  void setViewHolderData(int position, DataType bean){
        this.position=position;
        this.bean=bean;

        onBindView(position,bean);
    }
    protected String getString(int res){
        return getContext().getString(res);
    }
    protected Context getContext(){
        return behavior.getLifeCircleContext().getContext();
    }
    protected <V extends View> V findViewById(int res){
        return (V)itemView.findViewById(res);
    }
}
