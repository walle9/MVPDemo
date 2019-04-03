package com.example.mvpdemo.di.module;


import com.example.mvpdemo.base.MyApplication;
import com.example.mvpdemo.model.DataHelper;
import com.example.mvpdemo.model.http.HttpHelper;
import com.example.mvpdemo.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Desc:
 */
@Module
public class AppModule {
  private MyApplication application;

  public AppModule(MyApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
    return retrofitHelper;
  }

  @Provides
  @Singleton
  DataHelper provideDataHelper(HttpHelper httpHelper) {
    return new DataHelper(httpHelper);//生成全局单例
  }
}
