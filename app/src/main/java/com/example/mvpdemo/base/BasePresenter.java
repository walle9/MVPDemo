package com.example.mvpdemo.base;

/**
 * @date create at 2019/3/28 14:23
 * @describe 描述:presenter的基类接口
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T baseView);

    void detachView();
}
