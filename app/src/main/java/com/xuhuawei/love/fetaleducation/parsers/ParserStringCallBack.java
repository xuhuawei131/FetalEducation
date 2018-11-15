package com.xuhuawei.love.fetaleducation.parsers;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lingdian on 17/9/15.
 */

public abstract class ParserStringCallBack<ResultType> extends StringCallback {

    protected Scheduler getDataSchedule() {
        return Schedulers.io();
    }

    public abstract ResultType parserJson(Response<String> response);

    public abstract void onResultComing(ResultType response);

    /**
     * 对返回数据进行操作的回调， UI线程
     */
    public final void onSuccess(Response<String> response) {
        onNetResultComming(response);
    }

    /**
     * 缓存成功的回调,UI线程
     */
    public final void onCacheSuccess(Response<String> response) {
        onNetResultComming(response);
    }

    /**
     * 请求网络结束后，UI线程
     */
    public final void onFinish() {

    }

    private void onNetResultComming(Response<String> response) {
        Scheduler scheduler = getDataSchedule();
        if (scheduler == null || scheduler == AndroidSchedulers.mainThread()) {
            ResultType data = parserJson(response);
            onResultComing(data);
        } else {
            Observable.just(response)
                    .observeOn(scheduler)
                    .map(new Func1<Response<String>, ResultType>() {
                        @Override
                        public ResultType call(Response<String> stringResponse) {
                            ResultType data = parserJson(stringResponse);
                            return data;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ResultType>() {
                        @Override
                        public void call(ResultType resultType) {
                            onResultComing(resultType);
                        }
                    });
        }
    }
}
