package com.example.mvpdemo.di.module;

import com.example.mvpdemo.BuildConfig;
import com.example.mvpdemo.di.qualifier.ApiUrl;
import com.example.mvpdemo.model.http.ProtocolHttp;
import com.example.mvpdemo.model.http.api.HttpApi;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Desc:网络请求的参数初始化
 */
@Module
public class HttpModule {

    @Provides
    @Singleton
    OkHttpClient.Builder providesOkHttpHelper() {
        //请求读写超时时间
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
    }

    @Provides
    @Singleton
    OkHttpClient provideClient(OkHttpClient.Builder builder) {

        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
            if (BuildConfig.DEBUG) {
                Logger.wtf("==okhttp== %s", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        return builder
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }


    @Provides
    @Singleton
    Retrofit.Builder providesRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Provides
    @Singleton
    @ApiUrl
    Retrofit providesApiRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ProtocolHttp.HTTP_HOST);//这里就是你的网络请求的url
    }


    @Provides
    @Singleton
    HttpApi provideApi(@ApiUrl Retrofit retrofit) {//这个就是我们最终需要的
        return retrofit.create(HttpApi.class);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String host) {
        return builder.client(client)
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())//添加gson自动解析，我们不用管
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


}
