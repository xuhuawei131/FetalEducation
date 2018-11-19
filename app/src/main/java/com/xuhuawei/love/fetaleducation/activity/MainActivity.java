package com.xuhuawei.love.fetaleducation.activity;


import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xhwbaselibrary.base.BaseActivity;
import com.xhwbaselibrary.interfaces.LifeCircleContext;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.fragment.MainAskFragment;
import com.xuhuawei.love.fetaleducation.fragment.MainStroyFragment;

public class MainActivity extends BaseActivity implements LifeCircleContext {
    private TextView text_title;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private  MainStroyFragment fragment1 = new MainStroyFragment();
    private  MainAskFragment fragment2 = new MainAskFragment();
    @Override
    protected void init() {

    }
    @Override
    protected int setContentView() {
        return R.layout.activity_main2;
    }

    @Override
    protected void findViewByIds() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        text_title=findMyViewById(R.id.text_title);

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
    }

    @Override
    protected void requestService() {
       FragmentManager fragmentManager = getSupportFragmentManager();
       FragmentTransaction transaction=fragmentManager.beginTransaction();
       transaction.replace(R.id.container, fragment1);
       transaction.commit();
    }
    @Override
    protected void onMyDestory() {

    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
