package com.xhwbaselibrary.interfaces;

import java.util.List;

/**
 * Created by Administrator on 2018/2/17 0017.
 */

public interface AdapterBehavior<DataType> {
    public  void notifyDataSetChanged() ;
    public LifeCircleContext getLifeCircleContext();
    public List<DataType> getArrayList();
}
