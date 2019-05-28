package com.zl.tech.mvp.ui.activity;

import com.airbnb.lottie.LottieAnimationView;
import com.zl.tech.R;
import com.zl.tech.mvp.base.activity.BaseActivity;
import com.zl.tech.mvp.base.BasePresenter;
import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.one_animation)
    LottieAnimationView mOneAnimation;
    @BindView(R.id.two_animation)
    LottieAnimationView mTwoAnimation;
    @BindView(R.id.three_animation)
    LottieAnimationView mThreeAnimation;
    @BindView(R.id.four_animation)
    LottieAnimationView mFourAnimation;
    @BindView(R.id.five_animation)
    LottieAnimationView mFiveAnimation;
    @BindView(R.id.six_animation)
    LottieAnimationView mSixAnimation;
    @BindView(R.id.seven_animation)
    LottieAnimationView mSevenAnimation;
    @BindView(R.id.eight_animation)
    LottieAnimationView mEightAnimation;
    @BindView(R.id.nine_animation)
    LottieAnimationView mNineAnimation;
    @BindView(R.id.ten_animation)
    LottieAnimationView mTenAnimation;

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        //设置动画

    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initToobar() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }



    @Override
    protected void onStop() {
        super.onStop();

        finish();
    }

}

