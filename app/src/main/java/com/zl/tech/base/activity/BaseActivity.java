package com.zl.tech.base.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;

import com.zl.tech.application.MyApplication;
import com.zl.tech.base.BaseView;
import com.zl.tech.base.presenter.BasePresenter;
import com.zl.tech.di.component.activity.BaseActivityComponent;
import com.zl.tech.di.component.activity.DaggerBaseActivityComponent;
import com.zl.tech.di.module.activity.ActivityMoudel;

import javax.inject.Inject;


/**
 * Created by zhanglu on 2019/05/28   QQ:1228717266
 */
public abstract class BaseActivity<P extends BasePresenter> extends AbstractSimpleActivity implements BaseView {

    @Inject
    protected P mPresenter;
    protected BaseActivityComponent activityComponent;

    @Override
    protected void onViewCreated() {
        if (mPresenter != null) {
            mPresenter.AttachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.Destory();
        }
    }

    @Override
    protected void initject() {
        activityComponent = DaggerBaseActivityComponent
                .builder()
                .wanAndroidAppComponent(MyApplication.getAppComponent())
                .activityMoudel(new ActivityMoudel(this))
                .build();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 20) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
