package fetaleducation.xuhuawei.com.fetaleducation.adapter;

import android.view.View;

import com.xhwbaselibrary.adapters.MyListBaseAdapter;
import com.xhwbaselibrary.interfaces.LifeCircleContext;

import java.util.List;

import fetaleducation.xuhuawei.com.fetaleducation.R;
import fetaleducation.xuhuawei.com.fetaleducation.adapter.viewholder.MainViewHolder;
import fetaleducation.xuhuawei.com.fetaleducation.bean.MainBean;

public class MainAdapter extends MyListBaseAdapter<MainViewHolder,MainBean> {

    public MainAdapter(LifeCircleContext context, List<MainBean> arrayList) {
        super(context, arrayList);
    }
    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.adapter_main;
    }
    @Override
    public MainViewHolder getViewHolder(View view) {
        return new MainViewHolder(this,view);
    }
}
