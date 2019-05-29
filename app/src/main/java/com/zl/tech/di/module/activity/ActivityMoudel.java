package com.zl.tech.di.module.activity;

import android.app.Activity;
import android.content.Context;

import com.trello.rxlifecycle3.components.RxActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2019/5/29
 */

@Module
public class ActivityMoudel {

    private Activity activity;

    public ActivityMoudel(Activity activity) {
        this.activity = activity;
    }

    @Provides
    public Context provideContext(){
        return this.activity;
    }

    @Provides
    public Activity provideActivity(){
        return this.activity;
    }
}
