package com.xhwbaselibrary.http.executor;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.BaseBodyRequest;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.xhwbaselibrary.http.XhwHttp;
import com.xhwbaselibrary.http.request.XhwRequest;
import com.xhwbaselibrary.http.response.BaseSSResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.xhwbaselibrary.http.ErrorCode.NET_ERROR;
import static com.xhwbaselibrary.http.ErrorCode.UNKNOWN_ERROR;


/**
 * http执行的核心 在此！
 * Created by xuhuawei on 2017/6/6 0006.
 */

public class HttpExecutor {

    private static HttpExecutor instance=null;
    private HttpExecutor(){
        
    }
    public  static HttpExecutor getInstance(){
        if(instance==null){
            instance=new HttpExecutor();
        }
        return instance;
    }

    public void cancel(String tag) {

    }

    public void cancel(XhwHttp mageRequest) {
    }

    public void sendGet(XhwRequest xhwRequest) {
        GetRequest getRequest = OkGo.get(xhwRequest.getUrl());
        excuteRequest(getRequest, xhwRequest);
    }

    public void sendPost(XhwRequest xhwRequest) {
        PostRequest postRequest = OkGo.post(xhwRequest.getUrl());
        excuteRequest(postRequest, xhwRequest);
    }

    public void upload(XhwRequest xhwRequest) {

    }


    public void download(XhwRequest xhwRequest) {

    }

    /**
     * 执行普通Http、Https请求
     * @param request
     * @param mageRequest
     */
    private void excuteRequest(BaseRequest request,  XhwRequest mageRequest){
        copyRequest(request,mageRequest);
         final BaseSSResponse ssResponse=mageRequest.getResponseProxy();
        request.execute(new StringCallback() {
            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                ssResponse.beforeSend();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if(response != null){
                    ssResponse.onResultFailed(response.code(),response.message());
                }else if(e != null && e instanceof IOException){
                    ssResponse.onResultFailed(NET_ERROR,"网络错误!");
                }else {
                    ssResponse.onResultFailed(UNKNOWN_ERROR,"请求出错了!");
                }
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                ssResponse.afterResponse();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                ssResponse.onResultBack(s);
            }
        });
    }


    /**
     * 将MageRequest的参数放入到请求框架中
     * @param request
     * @param mageRequest
     */
    protected void copyRequest(BaseRequest request, XhwRequest mageRequest){
        if(!TextUtils.isEmpty(mageRequest.getTag())){
            request.tag(mageRequest.getTag());
        }

        if(mageRequest.getHeaderMap() != null && mageRequest.getHeaderMap().size() > 0){
            HttpHeaders headers = new HttpHeaders();
            headers.headersMap.putAll(mageRequest.getHeaderMap());
            request.headers(headers);
        }

        if(mageRequest.getParamMap() != null && mageRequest.getParamMap().size() > 0){
            request.params(mageRequest.getParamMap());
        }

        if(request instanceof BaseBodyRequest){
            BaseBodyRequest bodyRequest = (BaseBodyRequest) request;
            if(mageRequest.getFileMap() != null && mageRequest.getFileMap().size() > 0){
                bodyRequest.isMultipart(true);

                HashMap<String, ArrayList<File>> fileMap = mageRequest.getFileMap();

                for(Map.Entry<String,ArrayList<File>> entry:fileMap.entrySet()){
                    bodyRequest.addFileParams(entry.getKey(),entry.getValue());
                }
            }
        }
    }
}
