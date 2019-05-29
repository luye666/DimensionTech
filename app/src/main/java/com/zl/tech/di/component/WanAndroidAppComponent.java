package com.zl.tech.di.component;
import android.app.Application;
import com.zl.tech.di.module.WanAndroidAppModule;

import dagger.Component;

/**
 * Created by Administrator on 2019/5/28
 */

@Component(modules = WanAndroidAppModule.class)
public interface WanAndroidAppComponent {

        Application application();
}
