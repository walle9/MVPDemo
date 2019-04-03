package com.example.mvpdemo.presenter;


import com.example.mvpdemo.base.BaseMvpPresenter;
import com.example.mvpdemo.base.MyApplication;
import com.example.mvpdemo.contract.MainContract;
import com.example.mvpdemo.model.DataHelper;
import com.example.mvpdemo.model.bean.WeatherBean;
import com.example.mvpdemo.model.http.HttpResult;
import com.example.mvpdemo.model.http.MyDisposableObserver;
import com.example.mvpdemo.model.http.MyRxUtils;
import com.example.mvpdemo.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

/**
 * @date create at 2019/3/28 15:22
 * @describe 描述:
 */
public class MainPresenter extends BaseMvpPresenter<MainContract.IView> implements MainContract.Presenter {
    private DataHelper dataHelper;

    @Inject
    MainPresenter() {
        dataHelper = MyApplication.getAppComponent().getDataHelper();
    }

    @Override
    public void loadData() {
        /*addSubscribe(dataHelper.loginCode("189xxxxxxx")
                .compose(MyRxUtils.toMain(Schedulers.io()))
                .doOnSubscribe(disposable -> baseView.showLoading())
                .subscribeWith(new MyDisposableObserver<HttpNoResult>(baseView) {
                    @Override
                    public void onNext(HttpNoResult httpNoResult) {
                        ToastUtils.showTipMsg("网络请求成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showTipMsg("网络请求失败");
                    }
                })

        );*/

        addSubscribe(dataHelper.weather("蚌埠")
            .compose(MyRxUtils.toMain(Schedulers.io()))
                .doOnSubscribe(disposable -> baseView.showLoading())
                .subscribeWith(new MyDisposableObserver<HttpResult<WeatherBean>>(baseView) {
                    @Override
                    public void onNext(HttpResult<WeatherBean> weatherBeanHttpResult) {
                        ToastUtils.showTipMsg("网络请求成功");
                        Logger.d(weatherBeanHttpResult.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.showTipMsg("网络请求失败");
                    }
                })
        );
    }
}
