package com.example.mvpdemo.base;

import javax.inject.Inject;

/**
 * @date create at 2019/3/28 14:33
 * @describe 描述:所有使用mvp模式的Activity的父类
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {

    @Inject
    protected T basePresenter;

    @Override
    @SuppressWarnings("unchecked")
    protected void initView() {
        super.initView();
        initInject();
        if (null != basePresenter) {
            //V层和P层绑定,保证生命周期同步
            basePresenter.attachView(this);
        }
    }

    protected abstract void initInject();

    @Override
    protected void onDestroy() {
        //解除V层和P层绑定,保证生命周期同步,防止泄露
        if (null != basePresenter) {
            basePresenter.detachView();
            basePresenter = null;
        }
        super.onDestroy();
    }
}
