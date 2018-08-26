package com.xuhuawei.love.fetaleducation.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xhwbaselibrary.base.BaseRefreshMoreViewActivity;
import com.xhwbaselibrary.base.MyEntry;
import com.xhwbaselibrary.permission.MyPermissionTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.xhwbaselibrary.persistence.MySharedManger;
import com.xuhuawei.love.fetaleducation.adapter.MainAdapter;
import com.xuhuawei.love.fetaleducation.bean.MainBean;
import com.xuhuawei.love.fetaleducation.bean.PlayingAudioBean;
import com.xuhuawei.love.fetaleducation.config.SingleCacheData;
import com.xuhuawei.love.fetaleducation.files.FileSortCompare;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.player.MyPlayerApi;
import com.xuhuawei.love.fetaleducation.player.MyPlayerService;
import com.xuhuawei.love.fetaleducation.utils.FileUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_HOME_ITEM_CLICK;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_SERVICE_PAUSE_OR_START;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_STARTOR_PAUSE;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_START_NEW_AUDIO;
import static com.xuhuawei.love.fetaleducation.config.ShareConfig.SHARED_KEY_LAST_AUDIO;

public class MainActivity extends BaseRefreshMoreViewActivity {
    private String permission[] = {WRITE_EXTERNAL_STORAGE};
    private List<MainBean> arrayList = new ArrayList<>(0);

    private View layout_empty;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private View layout_detail_bar;
    private TextView text_bar_name;

    private ImageView ivPlayOrPause;
    private ImageView ivNext;
    private boolean isFirstRequest = true;

    private int currentIndex = 0;
    private boolean isDoingRequest = false;

    @Override
    protected void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int getJRefreshLayoutId() {
        return R.id.refreshLayout;
    }

    @Override
    protected void findRefreshMoreViewByIds() {
        layout_empty = findViewById(R.id.layout_empty);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        layout_detail_bar = findViewById(R.id.layout_detail_bar);
        text_bar_name = findViewById(R.id.text_bar_name);
        ivPlayOrPause = findViewById(R.id.ivPlayOrPause);
        ivNext = findViewById(R.id.ivNext);

        ivPlayOrPause.setOnClickListener(listener);
        ivNext.setOnClickListener(listener);
        layout_detail_bar.setOnClickListener(listener);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.layout_navi_left);

        View text_downloading = headerLayout.findViewById(R.id.text_downloading);
        View text_downloaded = headerLayout.findViewById(R.id.text_downloaded);
        View text_setting = headerLayout.findViewById(R.id.text_setting);
        View text_exit = headerLayout.findViewById(R.id.text_exit);


        String lastAudio = MySharedManger.getInstance().getStringValue(SHARED_KEY_LAST_AUDIO);
        if (TextUtils.isEmpty(lastAudio)) {
            layout_detail_bar.setVisibility(View.GONE);
        } else {
            layout_detail_bar.setVisibility(View.VISIBLE);
            text_bar_name.setText(lastAudio);
            boolean isPlaying = SingleCacheData.getInstance().isPlayingAudio();
            if (!isPlaying) {
                ivPlayOrPause.setImageResource(R.drawable.ic_play);
            } else {
                ivPlayOrPause.setImageResource(R.drawable.ic_pause);
            }
        }

        MainAdapter adapter;
        adapter = new MainAdapter(this, arrayList);
        setAdapter(adapter);
    }

    @Override
    protected RefreshReturnType doStartTask() {
        return RefreshReturnType.SUB_THREAD;
    }

    @Override
    protected Object doRefreshTask() {
        File fileList[] = FileUtils.getTencentFileList();
        if (fileList != null && fileList.length > 0) {

            String lastAudio = MySharedManger.getInstance().getStringValue(SHARED_KEY_LAST_AUDIO);
            List<File> fileArray = Arrays.asList(fileList);
            Collections.sort(fileArray, new FileSortCompare());
            List<MainBean> arrayList = new ArrayList<>(0);

            for (File file : fileArray) {
                MainBean itemBean = new MainBean(file);
                if (itemBean.title.equals(lastAudio)) {
                    itemBean.isSelect = true;
                } else {
                    itemBean.isSelect = false;
                }
                arrayList.add(itemBean);
            }
            return arrayList;
        }
        return null;
    }

    @Override
    protected void doRefreshEndingTask(Object result) {
        List<MainBean> tempList = (List<MainBean>) result;

        if (tempList != null && tempList.size() > 0) {
            arrayList.clear();
            layout_empty.setVisibility(View.GONE);
            refresh_layout.setVisibility(View.VISIBLE);

            arrayList.addAll(tempList);
            notifyDataSetChanged();
        } else {
            layout_empty.setVisibility(View.VISIBLE);
            refresh_layout.setVisibility(View.GONE);
        }
        SingleCacheData.getInstance().setCurrentList(arrayList);
    }

    @Override
    protected void onMoreTask() {

    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.recyclerView;
    }

    @Override
    protected void requestService() {
        checkRuntimePermission(new MyPermissionTask(permission) {
            @Override
            public void onPermissionDenied(String[] var1) {
            }

            @Override
            public void allPermissionGranted() {
                setAutoRefreshView();
            }
        });
    }

    @Override
    protected void onMyDestory() {
        EventBus.getDefault().unregister(this);
        drawer.removeDrawerListener(toggle);
    }

    /**
     * 播放新的音频时候
     *
     * @param bean
     */
    @Subscriber(tag = TAG_PLAY_UI_START_NEW_AUDIO)
    private void onStartNewAudio(PlayingAudioBean bean) {
        layout_detail_bar.setVisibility(View.VISIBLE);
        text_bar_name.setText(bean.itemId);
        MySharedManger.getInstance().putKeyAndValue(new MyEntry(SHARED_KEY_LAST_AUDIO, bean.itemId));

        for (MainBean itemBean : arrayList) {
            if (itemBean.title.equals(bean.itemId)) {
                itemBean.isSelect = true;
            } else {
                itemBean.isSelect = false;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 点击音频item
     *
     * @param position
     */
    @Subscriber(tag = TAG_HOME_ITEM_CLICK)
    private void onClickAudioStart(int position) {
        MainBean bean = arrayList.get(position);
        //缓存播放列表
        SingleCacheData.getInstance().setCurrentList(arrayList);
        //播放制定的音频
        MyPlayerService.startPlay(bean);
    }

    /**
     * 暂停状态
     *
     * @param isStart
     */
    @Subscriber(tag = TAG_PLAY_UI_STARTOR_PAUSE)
    private void onUIPauseOrStart(boolean isStart) {
        if (isStart) {
            ivPlayOrPause.setImageResource(R.drawable.ic_play);
        } else {
            ivPlayOrPause.setImageResource(R.drawable.ic_pause);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.ivPlayOrPause) {
                if (MyPlayerService.isRunningPlayingService()) {
                    EventBus.getDefault().post("", TAG_PLAY_SERVICE_PAUSE_OR_START);
                } else {
                    String lastAudio = MySharedManger.getInstance().getStringValue(SHARED_KEY_LAST_AUDIO);
                    if (!TextUtils.isEmpty(lastAudio)) {
                        MainBean bean = new MainBean(lastAudio);
                        //播放制定的音频
                        MyPlayerService.startPlay(bean);
                        EventBus.getDefault().post(false, TAG_PLAY_UI_STARTOR_PAUSE);
                    }
                }
            } else if (view.getId() == R.id.ivNext) {
                MyPlayerService.startPlayNext();
            } else if (view.getId() == R.id.layout_detail_bar) {
                //跳转详情页面
                Intent intent = new Intent(MainActivity.this, PlayingActivity.class);
                startActivity(intent);
            }
        }
    };
}
