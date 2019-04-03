package com.example.mvpdemo.model.http;

import com.example.mvpdemo.model.bean.WeatherBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * @date create at 2019/4/2 17:55
 * @describe 描述:网络接口，接口参数Token统一处理，方法中不传Token
 */
public interface HttpHelper {
    /**
     * 登录时获取验证码.
     *
     * @param phone 手机号
     * @return {"code":0}
     */
    Observable<HttpNoResult> loginCode(String phone);
    Observable<HttpResult<WeatherBean>> weather(String city);
    /*Flowable<HttpResult<Login>> login(String phone, String code);
    Flowable<HttpResult<List<DiyBean>>> diyKeys(String allId);*/
}
