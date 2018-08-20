package com.xhwbaselibrary.http.executor;

import android.text.TextUtils;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.BaseBodyRequest;
import com.lzy.okgo.request.BaseRequest;
import com.xhwbaselibrary.http.request.XhwRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class BaseExecutor {
    /**
     * 将MageRequest的参数放入到请求框架中
     * @param request
     * @param mageRequest
     */
    protected void buildParams(BaseRequest request, XhwRequest mageRequest){

//        MageLog.i(mageRequest.toString());

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
