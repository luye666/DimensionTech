package com.zl.tech.application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author Administrator QQ:1228717266
 * @name DimensionTech
 * @class name：com.wd.tech.application
 * @time 2018/11/29 19:57
 */
public class MyApplication extends MultiDexApplication {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        setContext(this);
        //        LeakCanary解决内存泄漏的问题
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);

        initSDPath();
    }

    private void initSDPath() {
        // 高级初始化：
        DiskCacheConfig images = DiskCacheConfig.newBuilder(this).setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory()).build();//设置磁盘缓存    


        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this).setMainDiskCacheConfig(images)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter());
        Fresco.initialize(this, imagePipelineConfig); //不设置默认传一个参数既可    
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
}
