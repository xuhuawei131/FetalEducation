package com.xhwbaselibrary.base;

/**
 * Created by lingdian on 17/9/27.
 */

public class MyEntry<V> {
    private String key;
    private V value;

    public MyEntry(String key,V value){
        this.key=key;
        this.value=value;
    }

    public void put(String key,V value){
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
