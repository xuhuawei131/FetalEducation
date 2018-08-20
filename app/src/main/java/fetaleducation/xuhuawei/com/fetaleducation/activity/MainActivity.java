package fetaleducation.xuhuawei.com.fetaleducation.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.xhwbaselibrary.base.BaseActivity;
import com.xhwbaselibrary.permission.MyPermissionTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fetaleducation.xuhuawei.com.fetaleducation.adapter.MainAdapter;
import fetaleducation.xuhuawei.com.fetaleducation.bean.MainBean;
import fetaleducation.xuhuawei.com.fetaleducation.files.FileSortCompare;
import fetaleducation.xuhuawei.com.fetaleducation.R;
import fetaleducation.xuhuawei.com.fetaleducation.utils.FileUtils;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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


    private void refreshData() {
        File fileList[] = FileUtils.getTencentFile();
        List<File> fileArray = Arrays.asList(fileList);
        Collections.sort(fileArray, new FileSortCompare());

        for (File file : fileArray) {
            arrayList.add(new MainBean(file));
        }
        adapter.notifyDataSetChanged();
    }

}
