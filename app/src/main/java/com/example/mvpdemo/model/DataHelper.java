package com.example.mvpdemo.model;

import com.example.mvpdemo.model.bean.WeatherBean;
import com.example.mvpdemo.model.http.HttpHelper;
import com.example.mvpdemo.model.http.HttpNoResult;
import com.example.mvpdemo.model.http.HttpResult;

import io.reactivex.Observable;

/**
 * @date create at 2019/4/2 18:05
 * @describe 描述:网络请求的实现类
 */
public class DataHelper implements HttpHelper {

    private HttpHelper http;

    /**
     * 参数是HttpHelper的实现类,在这里是RetrofitHelper
     * @param http
     */
    public DataHelper(HttpHelper http) {
        this.http = http;
    }

    @Override
    public Observable<HttpNoResult> loginCode(String phone) {
        return http.loginCode(phone);
    }

    @Override
    public Observable<HttpResult<WeatherBean>> weather(String city) {
        return http.weather(city);
    }
}
