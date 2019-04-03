package com.example.mvpdemo.model.http;

import com.example.mvpdemo.model.bean.WeatherBean;
import com.example.mvpdemo.model.http.api.HttpApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @date create at 2019/4/2 18:09
 * @describe 描述:网络接口Retrofit实现
 */
public class RetrofitHelper implements HttpHelper {
    private HttpApi httpApi;

    @Inject
    RetrofitHelper(HttpApi httpApi) {
        this.httpApi = httpApi;
    }

    @Override
    public Observable<HttpNoResult> loginCode(String phone) {
        return httpApi.loginCode(phone);
    }

    @Override
    public Observable<HttpResult<WeatherBean>> weather(String city) {
        return httpApi.weather(city);
    }
}
