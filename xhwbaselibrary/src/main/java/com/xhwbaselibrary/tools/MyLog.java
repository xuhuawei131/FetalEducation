package com.xhwbaselibrary.tools;

import com.orhanobut.logger.Logger;

/**
 * Created by lingdian on 17/9/26.
 */

public class MyLog {
    public static final String TAG="xhw";
    static{

    }

    public static void v(String content){
        Logger.init(content);
    }

}
