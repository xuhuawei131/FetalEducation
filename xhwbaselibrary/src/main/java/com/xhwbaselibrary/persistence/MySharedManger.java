package com.xhwbaselibrary.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.xhwbaselibrary.base.MyEntry;
import com.xhwbaselibrary.caches.MyAppContext;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by lingdian on 17/9/26.
 */

public class MySharedManger {

    private static MySharedManger instance;
    private SharedPreferences sharedPreferences;
    private static final String fileName = "xhw.init";

    private MySharedManger() {
        Context ctx = MyAppContext.getInstance().getContext();
        sharedPreferences = ctx.getSharedPreferences
                (fileName, Context.MODE_PRIVATE);
    }

    public static MySharedManger getInstance() {
        if (instance == null) {
            instance = new MySharedManger();
        }
        return instance;
    }

    /**
     * 设置shared
     * @param entry
     */
    public void putKeyAndValue(MyEntry entry) {
        if (entry != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
                Object value = entry.getValue();
                String key = entry.getKey();
                if (value instanceof Integer) {
                    editor.putInt(key, (int) value);
                } else if (value instanceof String) {
                    editor.putString(key, (String) value);
                } else if (value instanceof Float) {
                    editor.putFloat(key, (float) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (long) value);
                } else if (value instanceof Boolean) {
                    editor.putBoolean(key, (boolean) value);
                } else if (value instanceof Set) {
                    editor.putStringSet(key, (Set<String>) value);
                }else{//不支持的类型

                }
            editor.apply();
        }
    }

    public long getLongValue(String key){
        return sharedPreferences.getLong(key,-1);
    }
    public int getIntValue(String key){
        return sharedPreferences.getInt(key,-1);
    }
    public String getStringValue(String key){
        return sharedPreferences.getString(key,"");
    }
    public boolean getBooleanValue(String key){
        return sharedPreferences.getBoolean(key,false);
    }
}
