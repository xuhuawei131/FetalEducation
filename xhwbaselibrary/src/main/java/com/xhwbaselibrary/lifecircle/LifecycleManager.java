package com.xhwbaselibrary.lifecircle;

import java.util.ArrayList;
import java.util.List;

/**
 * 生命周期管理
 * Created by 许华维 on 2017/6/5 0005.
 */
public class LifecycleManager {
    private List<Lifecycle> monitorList = new ArrayList<>();

    public void add(Lifecycle lifecycle){
        monitorList.add(lifecycle);
    }
    public void remove(Lifecycle lifecycle){
        monitorList.remove(lifecycle);
    }

    public void remove(String id){
        for (Lifecycle lifecycle : monitorList) {
            if(lifecycle.getId().equals(id)){
                monitorList.remove(lifecycle);
                break ;
            }
        }

    }
    public void onCreate() {

    }
    public void onResume(){
        for (Lifecycle lifecycle : monitorList) {
            lifecycle.onResume();
        }
    }

    public void onPause(){
        for (Lifecycle lifecycle : monitorList) {
            lifecycle.onPause();
        }
    }

    public void onStop(){
        for (Lifecycle lifecycle : monitorList) {
            lifecycle.onStop();
        }
    }

    public void onDestroy(){
        for (Lifecycle lifecycle : monitorList) {
            lifecycle.onDestroy();
        }
        monitorList.clear();
//        monitorList = null;
    }


}
