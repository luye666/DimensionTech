package com.zl.tech.mvp.ui.activity;



import com.zl.tech.R;
import com.zl.tech.mvp.base.BaseActivity;
import com.zl.tech.mvp.base.BasePresenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends BaseActivity {


    @BindView(R.id.guide)
    ImageView guide;

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

        //设置动画
        setAplanAnimation();

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private void setAplanAnimation() {
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(3000);

        alpha.setFillAfter(true);

        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        guide.setAnimation(alpha);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

