package com.example.mvpdemo.model.http.api;

import com.example.mvpdemo.model.bean.WeatherBean;
import com.example.mvpdemo.model.http.HttpNoResult;
import com.example.mvpdemo.model.http.HttpResult;
import com.example.mvpdemo.model.http.ProtocolHttp;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @date create at 2019/4/2 18:12
 * @describe 描述:api
 */
public interface HttpApi {
    /**
     * 登录时获取验证码.
     *
     * @param phone 手机号
     * @return {"code":0}
     */
    @FormUrlEncoded
    @POST(ProtocolHttp.METHOD_LOGIN_CODE)
    Observable<HttpNoResult> loginCode(@Field("phone") String phone);

    /**
     * 获取天气信息
     * @param city
     * @return
     */
    @GET(ProtocolHttp.METHOD_WEATHER)
    Observable<HttpResult<WeatherBean>> weather(@Query("city") String city);
}
