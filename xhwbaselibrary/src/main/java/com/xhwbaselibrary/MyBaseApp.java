package com.xhwbaselibrary;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.xhwbaselibrary.caches.MyAppContext;
import com.xhwbaselibrary.caches.MyBackCheckHelper;
import com.xhwbaselibrary.caches.MyNetStatusHepler;
import com.xhwbaselibrary.configs.BaseConfig;
import com.xhwbaselibrary.crashes.CrashHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by lingdian on 17/9/25.
 */

public abstract class MyBaseApp extends Application {
    public static Context context;
    @Override
    public final void onCreate() {
        super.onCreate();
        if (isAppMainProcess()) {
            context=this;
            MyAppContext.getInstance().init(this);
            //崩溃日志收集
            CrashHandler.getInstance();
            //网络状态判断
            MyNetStatusHepler.getInstance().register();
            //前后台 状态监测
            MyBackCheckHelper.getInstance().isBackground();

            initOkGo();

            initLog();

            onlyInitOnce();
        }
    }

    /**
     * 判断是否运行在主进程中
     *
     * @return 是否为主进程
     */
    private boolean isAppMainProcess() {
        try {
            int pid = android.os.Process.myPid();
            String process = getAppNameByPID(this, pid);
            if (TextUtils.isEmpty(process)) {
                return true;
            } else return this.getPackageName().equalsIgnoreCase(process);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 根据Pid得到进程名
     */
    private String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }
    /**
     * 在多进程App中，只在主进程初始化一次的代码放到这里
     */
    public abstract void onlyInitOnce();

private void initLog(){
    Logger.init("xhw")// tag值
            .methodCount(2)// 显示多少个堆栈中的方法个数
            .logLevel(LogLevel.FULL)// default LogLevel.FULL
            .methodOffset(0)// 方法显示的偏移量 如果是0
            .hideThreadInfo();// 是否显示线程的信息
}
    private void initOkGo(){
        //必须调用初始化
        OkGo.getInstance().init(MyAppContext.getInstance().getApplication());

        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();//header不支持中文，不允许有特殊字符
        for(Map.Entry<String,String> entry: BaseConfig.headerMap.entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            headers.put(key, value);
        }

        HttpParams params = new HttpParams(); //param支持中文,直接传,不要自己编码
        for(Map.Entry<String,String> entry: BaseConfig.commMap.entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            params.put(key, value);
        }

        //-----------------------------------------------------------------------------------//

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
        OkGo.getInstance()
                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                .setCacheMode(BaseConfig.CACHE_MODE)
                //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                .setCacheTime(BaseConfig.ERROR_TIME)
                //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(BaseConfig.ERROR_TIME)
                //这两行同上，不需要就不要加入
                .addCommonHeaders(headers)  //设置全局公共头
                .addCommonParams(params);   //设置全局公共参数


        OkHttpClient client = new OkHttpClient();
        client.newBuilder().readTimeout(BaseConfig.READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(BaseConfig.WRITE_TIME, TimeUnit.SECONDS)
                .connectTimeout(BaseConfig.CONNECT_TIME, TimeUnit.SECONDS);
//        OkGo.getInstance().setOkHttpClient(client);
    }

}
