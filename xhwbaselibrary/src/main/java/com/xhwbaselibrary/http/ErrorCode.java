package com.xhwbaselibrary.http;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class ErrorCode {
    /**
     * 在请求时出现了Http错误 例如：404、500等
     */
    public static final int HTTP_ERROR = -10000 ;
    /**
     * 网络出现问题，例如：无可用网络
     */
    public static final int NET_ERROR = -10001 ;
    /**
     * 难以界定的问题
     */
    public static final int UNKNOWN_ERROR = -10002 ;
}
