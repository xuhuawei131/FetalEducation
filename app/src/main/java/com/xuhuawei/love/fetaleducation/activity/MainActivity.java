package com.xuhuawei.love.fetaleducation.activity;


import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.BaseRequest;
import com.xhwbaselibrary.base.BaseRefreshMoreViewActivity;
import com.xhwbaselibrary.interfaces.LifeCircleContext;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.adapter.HomePageAdapter;
import com.xuhuawei.love.fetaleducation.bean.StoryBean;
import com.xuhuawei.love.fetaleducation.parsers.ParserStringCallBack;
import com.xuhuawei.love.fetaleducation.utils.HtmlPageUrlUtils;
import com.xuhuawei.love.fetaleducation.utils.HtmlParer;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

public class MainActivity extends BaseRefreshMoreViewActivity implements LifeCircleContext {
    private List<StoryBean> arrayList;
    private int currentIndex = 0;
    private boolean isDoingRequest = false;


    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private boolean  isFirstRequest=true;
    @Override
    protected void init() {

    }

    @Override
    protected int setContentView() {
        return R.layout.activity_main2;
    }

    @Override
    protected void requestService() {
        EventBus.getDefault().register(this);
        if (isDoingRequest) {
            return;
        }
        isDoingRequest = true;

        OkGo.<String>post(HtmlPageUrlUtils.getPageUrlByIndex(0)).tag(this).execute(new ParserStringCallBack<List<StoryBean>>() {

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                showProgressDialog();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                notifyEmptyAdapter();
                disProgressDialog();
            }


            @Override
            public List<StoryBean> parserJson(Response<String> response) {
                List<StoryBean> dataList = HtmlParer.dealFileListResult(response);
                return dataList;
            }
            @Override
            public void onResultComing(List<StoryBean> response) {
                disProgressDialog();
                isDoingRequest = false;

                arrayList.clear();
                arrayList.addAll(response);

                notifyDataSetChanged();
                notifyEmptyAdapter();
                setRefreshFinish();
                if(isFirstRequest){
                    isFirstRequest=false;
//                    showWaittingDialog();
                }
            }
        });
    }

    @Override
    protected void onMyDestory() {

    }
    @Override
    protected int getJRefreshLayoutId() {
        return R.id.refreshLayout;
    }
    @Override
    protected int getRecyclerViewId() {
        return R.id.recyclerView;
    }


    @Override
    protected void findRefreshMoreViewByIds() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.layout_navi_left);

        View text_downloading=headerLayout.findViewById(R.id.text_downloading);
        View text_downloaded=headerLayout.findViewById(R.id.text_downloaded);
        View text_setting=headerLayout.findViewById(R.id.text_setting);
        View text_exit=headerLayout.findViewById(R.id.text_exit);

        text_downloading.setOnClickListener(clickListener);
        text_downloaded.setOnClickListener(clickListener);
        text_setting.setOnClickListener(clickListener);
        text_exit.setOnClickListener(clickListener);

        arrayList = new ArrayList<>();
        HomePageAdapter adapter = new HomePageAdapter(this,arrayList);
        setAdapter(adapter);
    }

    @Override
    protected RefreshReturnType doStartTask() {
        return RefreshReturnType.UI_THREAD;
    }
    @Override
    protected Object doRefreshTask() {
        requestService();
        return null;
    }
    @Override
    protected void doRefreshEndingTask(Object o) {}

    @Override
    protected void onMoreTask() {

    }

    /**
     * 空提醒
     */
    private void notifyEmptyAdapter() {
        int length = arrayList.size();
        if (length == 0) {
            findViewById(R.id.textEmptyView).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.textEmptyView).setVisibility(View.GONE);
        }
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
