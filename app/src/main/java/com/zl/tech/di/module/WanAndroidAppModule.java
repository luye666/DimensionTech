package com.zl.tech.di.module;

import android.app.Application;

import com.zl.tech.application.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2019/5/28
 *
 * <p/>
 * Application的依赖工厂
 */

@Module
public class WanAndroidAppModule {

    Application application;

    public WanAndroidAppModule(MyApplication application) {
        this.application = application;
    }

//    @Singleton
    @Provides
    public Application provideApplication() {
        return application;
    }
}
