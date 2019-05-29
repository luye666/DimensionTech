package com.zl.tech.application;
import android.content.Context;
import androidx.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zl.tech.di.component.DaggerWanAndroidAppComponent;
import com.zl.tech.di.component.WanAndroidAppComponent;
import com.zl.tech.di.module.WanAndroidAppModule;

/**
 * Created by zhanglu on 2019/05/28   QQ:1228717266
 * <p/>
 * 基本作用：
 *  1.初始化以及集成LeakCanary，实现对内存的监听
 *  2.使用软引用，解决因App的上下文而造成的内存泄露
 *  3.判断是不是第一次进入应用
 *
 */
public class MyApplication extends MultiDexApplication {

    private RefWatcher refWatcher;

    private static WanAndroidAppComponent appComponent;

//    判断是不是第一次进入应用(默认为true)
    public static boolean isFirstRun = true;

    @Override
    public void onCreate() {
        super.onCreate();

        //        LeakCanary解决内存泄漏的问题
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);

//        初始化上下文
        setContext(this);


        appComponent = DaggerWanAndroidAppComponent.builder()
                .wanAndroidAppModule(new WanAndroidAppModule(this))
                .build();

    }

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }


    public static WanAndroidAppComponent getAppComponent() {
        return appComponent;
    }
}
