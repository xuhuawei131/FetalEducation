package com.xhwbaselibrary.configs;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lingdian on 17/9/25.
 */

public class BaseConfig {
    /**是否启动崩溃日志本地记录*/
    public static final boolean isSetupCrash=false;

    public static Map<String,String>  headerMap;
    public static Map<String,String>  commMap;
    public static  CacheMode CACHE_MODE=CacheMode.REQUEST_FAILED_READ_CACHE;
    public static  long EXPIRE_TIME = CacheEntity.CACHE_NEVER_EXPIRE;
    public static  int ERROR_TIME=3;
    public static  int READ_TIME=60;
    public static  int WRITE_TIME=60;
    public static  int PING_TIME=60;
    public static  int CONNECT_TIME=60;

    static{
        //初始化 okgo中的header以及comm
        headerMap=new HashMap<>();
        commMap=new HashMap<>();
    }

}
