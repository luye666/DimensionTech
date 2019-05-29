package com.zl.tech.di.component.activity;
import android.app.Activity;
import android.content.Context;

import com.zl.tech.di.annotation.ActivityScorp;
import com.zl.tech.di.component.WanAndroidAppComponent;
import com.zl.tech.di.module.activity.ActivityMoudel;
import com.zl.tech.ui.activity.MainActivity;
import dagger.Component;

/**
 * Created by Administrator on 2019/5/29
 */
@ActivityScorp
@Component(modules = ActivityMoudel.class,dependencies = WanAndroidAppComponent.class)
public interface BaseActivityComponent {

   Activity activity();

   Context context();

   void inject(MainActivity activity);
}
