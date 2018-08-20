package com.xhwbaselibrary.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/2/23 0023.
 */

public class LogUtils {
    private static final boolean DEBUG = true;

    public LogUtils() {
    }

    public static void v(String tag, String content) {
        Log.v(tag, content);
    }

    public static void d(String tag, String content) {
        Log.d(tag, content);
    }

    public static void i(String tag, String content) {
        Log.i(tag, content);
    }

    public static void w(String tag, String content) {
        Log.w(tag, content);
    }

    public static void e(String tag, String content) {
        Log.e(tag, content);
    }

    public static void v(String tag, String content, Throwable throwable) {
        Log.v(tag, content, throwable);
    }

    public static void d(String tag, String content, Throwable throwable) {
        Log.d(tag, content, throwable);
    }

    public static void i(String tag, String content, Throwable throwable) {
        Log.i(tag, content, throwable);
    }

    public static void w(String tag, String content, Throwable throwable) {
        Log.w(tag, content, throwable);
    }

    public static void e(String tag, String content, Throwable throwable) {
        Log.e(tag, content, throwable);
    }

    public static void w(String tag, Throwable throwable) {
        Log.w(tag, throwable);
    }
    public static void e(String tag, Throwable content) {
        Log.e(tag, content.getLocalizedMessage());
    }

    public static void d(String content) {
        Log.v("xhw", content);
    }
}
