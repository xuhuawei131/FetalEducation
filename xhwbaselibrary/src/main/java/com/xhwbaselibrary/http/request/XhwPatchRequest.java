package com.xhwbaselibrary.http.request;


import com.xhwbaselibrary.http.response.PatchSSResponse;

import java.util.List;

/**
 * 批量请求封装
 * Created by xuhuawei on 2017/6/6 0006.
 */

public class XhwPatchRequest {
    private List<XhwRequest> requestList;
    private PatchSSResponse patchSSResponse;

    public XhwPatchRequest(List<XhwRequest> requestList, PatchSSResponse patchSSResponse){

    }
}
