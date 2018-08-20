package com.xhwbaselibrary.http;

import android.text.TextUtils;

import com.xhwbaselibrary.http.request.XhwRequest;
import com.xhwbaselibrary.http.response.BaseSSResponse;
import com.xhwbaselibrary.utils.NetworkUtil;


/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class XhwRequestChecker {

    /**
     * 在发送请求前检查数据的完整性
     * @param request
     * @return
     */
    public static boolean check(XhwRequest request) throws RuntimeException {
        BaseSSResponse response=request.getResponseProxy();
        if(!NetworkUtil.isNetConnected()){
//            MageLog.e("Tag为 "+request.getTag()+" 的请求网络不可用!");
            response.onResultFailed(ErrorCode.NET_ERROR,"网络不可用");
            response.afterResponse();
            return false ;
        }

        if(TextUtils.isEmpty(request.getTag())){
            response.afterResponse();
            throw new RuntimeException("请求缺少tag参数!");
        }

        if(TextUtils.isEmpty(request.getUrl())){
            response.afterResponse();
            throw new RuntimeException("请求缺少url参数!");
        }

        if(TextUtils.isEmpty(request.getRequestDesc())){
            response.afterResponse();
//            MageLog.e("Tag为 "+request.getTag()+" 的请求没有设置文字描述，这可能不利于进行数据调试!");
        }

        if(request.getLifeCircleContext() == null){
//            MageLog.e("请求被废弃： Tag为 "+request.getTag()+" 的请求未关联任何Activity、Fragment或Context载体，" +
//                    "在请求完毕刷新UI时，可能会导致程序崩溃!");
            response.afterResponse();
            throw new RuntimeException("请求缺少tLifeCircleContext参数!");
        }
        return true ;

    }

}
