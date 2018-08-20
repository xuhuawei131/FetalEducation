package com.xhwbaselibrary.http;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.xhwbaselibrary.MyBaseApp;
import com.xhwbaselibrary.http.executor.HttpExecutor;
import com.xhwbaselibrary.http.request.XhwPatchRequest;
import com.xhwbaselibrary.http.request.XhwRequest;

/**
 * http 网络管理
 * Created by 许华维 on 2017/6/5 0005.
 */

public class XhwHttp {

    static {

    }

    public static void init(Application application) {

//        String type = "aphone"; //deviceType：设备类型（ipad、iphone、小米、华为等）
//        String version = "0.0.0.0";//appVersion:app客户端版本号
//        String userToken = "";//userToken：登录token
//        String sid = "200000";//channelCode：手机系统、渠道
//        String sVerison = "";//systemVersion：系统版本
//        String deviceID = "";//设备ID
//        String channelID = "";//channelID 渠道号
//        String resolution = "";//分辨率
//        String mac = "";//mac 地址
//        String whRatio = "";//屏幕宽高比
//
//        type = SSDevice.OS.getModel();
//        version = SSAppInfo.getVersionName(application);
//        sVerison = SSDevice.OS.getVersion();
//        deviceID = SSDevice.Dev.getDeviceID(application);
//        whRatio = SSDevice.Dev.getScreenRatio(application);
//        channelID = SSDevice.Dev.getChannelID(application);
//        resolution = SSDevice.Dev.getResolution(application);
//        mac = SSDevice.Wifi.getRawMacAddress(application);
//


//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");

//        HttpParams params = new HttpParams();
//        for(Map.Entry<String,String> item:defualtParam.entrySet()){
//            params.put(item.getValue(), item.getValue());     //所有的 params 都 支持 中文
//        }
//        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
//        params.put("commonParamsKey2", "这里支持中文参数");

        OkGo.init(application);
        OkGo.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    ;             //全局的写入超时时间
                //.setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                //.setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
//                .addCommonHeaders(headers)                                         //设置全局公共头
//                .addCommonParams(params);                                          //设置全局公共参数
    }

    public void startRequestUrl(XhwRequest request) {

    }

    public void startPatchRequestUrl(XhwPatchRequest patchRequest) {

    }


    private static XhwRequest createJYRequest(XhwRequest.RequestMethod requestType) {

        Context context = MyBaseApp.context;
//        XhwRequest request = new XhwRequest()
//                .setRequestType(requestType)
//                .addParam("clientid", JY_ClientUtil.getClientId())
//                .addParam("channelid", JY_ClientUtil.getChannelId())
//                .addParam("ver", AppUtil.getVersionName(context))
//                .addParam("lang", "zh-Hans")
//                .addParam("dd", DeviceUtil.getModel());//手机型号
//
//
//        request.addParam("osv", DeviceUtil.getSystemVersionName())//操作系统版本
//                .addParam("deviceid", DeviceUtil.getDeviceId(context))//设备id
//                .addParam("mac", DeviceUtil.getMac(context))//mac地址
//                .addParam("traceid", DeviceUtil.getAndroidId(context))
//                .addParam("token", JY_AppCache.getToken());

//        return request;
        return null;
    }


    /**
     * 构建Get请求
     *
     * @param tag
     * @return
     */
    public static XhwRequest get(@NonNull String tag) {
        XhwRequest request = new XhwRequest(tag)
                .setRequestType(XhwRequest.RequestMethod.GET);
        return request;
    }

    /**
     * 构建Post请求
     *
     * @param tag
     * @return
     */
    public static XhwRequest post(@NonNull String tag) {
        XhwRequest request = new XhwRequest(tag)
                .setRequestType(XhwRequest.RequestMethod.POST);
        return request;
    }

    /**
     * 根据tag取消网络请求
     *
     * @param tag
     */
    public static void cancelRequest(String tag) {
        HttpExecutor.getInstance().cancel(tag);
    }

    /**
     * 取消某个Request的网络请求
     *
     * @param mageRequest
     */
    public static void cancelRequest(XhwHttp mageRequest) {
        HttpExecutor.getInstance().cancel(mageRequest);
    }

}
