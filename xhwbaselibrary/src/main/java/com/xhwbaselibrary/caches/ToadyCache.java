package com.xhwbaselibrary.caches;

import com.xhwbaselibrary.base.MyEntry;
import com.xhwbaselibrary.persistence.MySharedManger;
import com.xhwbaselibrary.utils.DateUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lingdian on 17/9/26.
 * 今日缓存数据
 * 有的数据 过了今天
 */

public class ToadyCache {
    private static ToadyCache instance;
    public static final String KEY_4G_ALERT="alert";
    private ToadyCache(){

    }
    public static ToadyCache getInstance(){
        if (instance==null){
            instance=new ToadyCache();
        }
        return instance;
    }

    public boolean isShow4GDialog(){
        long lastTime=MySharedManger.getInstance().getLongValue("4gTime");
        long currentTime= System.nanoTime();
         boolean isSameDay=DateUtils.isSameDay(lastTime,currentTime);
        if (isSameDay){
            return true;
        }else{
            MyEntry<Long> entry=new MyEntry<>();
            entry.put("4gTime",currentTime);
            MySharedManger.getInstance().putKeyAndValue(entry);
            return false;
        }
    }


}
