package com.example.mvpdemo.model.http;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:
 */
public class MyRxUtils {

    /**
     * 从其他线程转到主线程.
     *
     * @param scheduler Schedulers.io()等等
     * @param <T>       t
     */

    public static <T> ObservableTransformer<T, T> toMain(Scheduler scheduler) {
        return upstream -> upstream.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<T, T> toMainOfFlowable(Scheduler scheduler) {
        return upstream -> upstream.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<HttpResult<T>, T> handResultOfFlowable() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(tHttpResult -> {
                    if (tHttpResult.getCode() == HttpCode.SUCCESS) {
                        return /*createData(tHttpResult.data)*/Flowable.just(tHttpResult.getData());
                    } else {
                        return Flowable.error(new ApiException(tHttpResult.getCode(), tHttpResult.getMsg()));
                    }
                });
    }

    public static <T> ObservableTransformer<HttpResult<T>, T> handResult() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(tHttpResult -> {
                    if (tHttpResult.getCode() == HttpCode.SUCCESS) {
                        return /*createData(tHttpResult.data)*/Observable.just(tHttpResult.getData());
                    } else {
                        return Observable.error(new ApiException(tHttpResult.getCode(), tHttpResult.getMsg()));
                    }
                });
    }

    /**
     * 生成Flowable
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Flowable<T> createDataOfFlowable(final T data) {
        return Flowable.create(e -> {
                e.onNext(data);
                e.onComplete();
        }, BackpressureStrategy.ERROR);
    }

    /**
     * 生成Observable
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(e -> {
                e.onNext(data);
                e.onComplete();
        });
    }
}
