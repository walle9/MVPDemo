package com.example.mvpdemo.di.component;


import com.example.mvpdemo.di.module.AppModule;
import com.example.mvpdemo.di.module.HttpModule;
import com.example.mvpdemo.model.DataHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    DataHelper getDataHelper();
}
