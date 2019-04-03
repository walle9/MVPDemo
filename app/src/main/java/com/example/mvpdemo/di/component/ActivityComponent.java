package com.example.mvpdemo.di.component;


import com.example.mvpdemo.di.module.ActivityModule;
import com.example.mvpdemo.di.scope.ActivityScope;
import com.example.mvpdemo.view.activity.MainActivity;

import dagger.Component;

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  void inject(MainActivity mainActivity);
  //void inject(LoginActivity loginActivity);
}
