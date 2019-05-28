package com.zl.tech.mvp.base.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;

import com.zl.tech.mvp.base.BasePresenter;
import com.zl.tech.mvp.base.BaseView;
import com.zl.tech.mvp.model.api.Contant;


/**
 * Created by zhanglu on 2019/05/28   QQ:1228717266
 */
public abstract class BaseActivity<P extends BasePresenter> extends AbstractSimpleActivity implements BaseView {

    protected P mpresenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//       传值
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        Contant.USER_ID =user.getInt("userId",0);
        Contant.SESSION_ID = user.getString("sessionId",null);


        if (mpresenter == null) {
            mpresenter = getPresenter();
        }

        if (mpresenter != null) {
            mpresenter.AttachView(this);
        }

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mpresenter != null){
            mpresenter.Destory();
        }
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

    protected abstract void initData();

    protected abstract P getPresenter();



}
