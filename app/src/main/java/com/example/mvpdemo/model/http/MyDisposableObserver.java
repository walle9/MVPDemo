package com.example.mvpdemo.model.http;

import com.example.mvpdemo.base.BaseView;

import io.reactivex.observers.DisposableObserver;

/**
 * @date create at 2019/4/3 10:16
 * @describe 描述:自定义DisposableObserver
 */
public abstract class MyDisposableObserver<T> extends DisposableObserver<T> {
    private BaseView baseView;

    public MyDisposableObserver(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onError(Throwable e) {
        if (null == baseView) {
            return;
        }
        baseView.hideLoading();
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            switch (apiException.getCode()) {
                case HttpCode.NO_PARAMETER:
                    baseView.showTipMsg("参数为空");
                    break;
                case HttpCode.SERVER_ERR:
                    baseView.showTipMsg("服务器错误");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onComplete() {
        if (null != baseView) {
            baseView.hideLoading();
        }
    }
}
