package com.example.mvpdemo.base;

import android.support.annotation.StringRes;

/**
 * @date create at 2019/3/28 11:33
 * @describe 描述:所有V层的基类
 */
public interface BaseView {
    void showTipMsg(String msg);

    void showTipMsg(@StringRes int msg);

    void showLoading();

    void hideLoading();

    void invalidToken();

    void myFinish();
}
