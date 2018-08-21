package com.xuhuawei.love.fetaleducation.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xhwbaselibrary.base.BaseActivity;
import com.xhwbaselibrary.permission.MyPermissionTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.xhwbaselibrary.persistence.MySharedManger;
import com.xuhuawei.love.fetaleducation.adapter.MainAdapter;
import com.xuhuawei.love.fetaleducation.bean.MainBean;
import com.xuhuawei.love.fetaleducation.config.SingleCacheData;
import com.xuhuawei.love.fetaleducation.files.FileSortCompare;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.player.MyPlayerService;
import com.xuhuawei.love.fetaleducation.utils.FileUtils;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.xuhuawei.love.fetaleducation.config.ShareConfig.SHARED_KEY_LAST_AUDIO;

public class MainActivity extends BaseActivity {
    private String permission[] = {WRITE_EXTERNAL_STORAGE};
    private ListView listView;
    private MainAdapter adapter;
    private List<MainBean> arrayList = new ArrayList<>();

    @Override
    protected void init() {
        adapter = new MainAdapter(this, arrayList);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViewByIds() {
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);
    }

    @Override
    protected void requestService() {
        checkRuntimePermission(new MyPermissionTask(permission) {
            @Override
            public void onPermissionDenied(String[] var1) {

            }

            @Override
            public void allPermissionGranted() {
//                Observable.just("").observeOn(AndroidSchedulers.mainThread()).observeOn()
                refreshData();
            }
        });
    }

    @Override
    protected void onMyDestory() {

    }

    private AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MainBean bean=arrayList.get(position);
            //缓存播放列表
            SingleCacheData.getInstance().setCurrentList(arrayList);
            //播放制定的音频
            MyPlayerService.startPlay(bean);
            //跳转详情页面
            Intent intent = new Intent(MainActivity.this, PlayingActivity.class);
            startActivity(intent);
        }
    };
    private void refreshData() {

        String lastAudio=MySharedManger.getInstance().getStringValue(SHARED_KEY_LAST_AUDIO);

        arrayList.clear();

        File fileList[] = FileUtils.getTencentFile();
        List<File> fileArray = Arrays.asList(fileList);
        Collections.sort(fileArray, new FileSortCompare());

        for (File file : fileArray) {
            MainBean itemBean=new MainBean(file);
            if (itemBean.title.equals(lastAudio)){
                itemBean.isSelect=true;
            }else{
                itemBean.isSelect=false;
            }
            arrayList.add(itemBean);
        }
        adapter.notifyDataSetChanged();
    }

}
