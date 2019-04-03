package com.example.mvpdemo.base;

import android.app.Application;
import android.support.annotation.Nullable;

import com.example.mvpdemo.BuildConfig;
import com.example.mvpdemo.di.component.AppComponent;
import com.example.mvpdemo.di.component.DaggerAppComponent;
import com.example.mvpdemo.di.module.AppModule;
import com.example.mvpdemo.di.module.HttpModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;

/**
 * @date create at 2019/3/27 10:26
 * @describe 描述:
 */
public class MyApplication extends Application {
    private static AppComponent appComponent;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    private static void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);

        /**------ 内存泄漏检测 ------**/
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 获取AppComponent.
     *
     * @return AppComponent
     */
    public static synchronized AppComponent getAppComponent() {
        if (null == appComponent) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(getInstance()))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }


}
