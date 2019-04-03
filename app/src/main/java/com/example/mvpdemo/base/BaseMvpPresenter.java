package com.example.mvpdemo.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @date create at 2019/3/28 14:24
 * @describe 描述:presenter的基类
 */
public class BaseMvpPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T baseView;
    private CompositeDisposable disposables;

    @Override
    public void attachView(T baseView) {
        this.baseView = baseView;
    }

    @Override
    public void detachView() {
        this.baseView = null;
    }

    protected void addSubscribe(Disposable disposable) {
        if (null == disposables) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    private void unSubscribe() {
        if (null != disposables) {
            disposables.clear();
            disposables = null;
        }
    }


}
