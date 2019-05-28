package com.zl.tech.mvp.ui.activity;

import android.content.Intent;

import com.airbnb.lottie.LottieAnimationView;
import com.zl.tech.R;
import com.zl.tech.application.MyApplication;
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
        mOneAnimation.setAnimation("W.json");
        mOneAnimation.playAnimation();
        mTwoAnimation.setAnimation("A.json");
        mTwoAnimation.playAnimation();
        mThreeAnimation.setAnimation("N.json");
        mThreeAnimation.playAnimation();
        mFourAnimation.setAnimation("A.json");
        mFourAnimation.playAnimation();
        mFiveAnimation.setAnimation("N.json");
        mFiveAnimation.playAnimation();
        mSixAnimation.setAnimation("D.json");
        mSixAnimation.playAnimation();
        mSevenAnimation.setAnimation("R.json");
        mSevenAnimation.playAnimation();
        mEightAnimation.setAnimation("I.json");
        mEightAnimation.playAnimation();
        mNineAnimation.setAnimation("O.json");
        mNineAnimation.playAnimation();
        mTenAnimation.setAnimation("D.json");
        mTenAnimation.playAnimation();
    }

    @Override
    protected void initToobar() {
        if (!MyApplication.isFirstRun) {
            goToShow();
            return;
        }

        MyApplication.isFirstRun = false;
    }

    private void goToShow() {
        Intent intent = new Intent(this, ShowActivity.class);
        startActivity(intent);

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

    @Override
    protected void onDestroy() {

        cancenAnimation();

        super.onDestroy();
    }

//    关闭动画
    private void cancenAnimation(LottieAnimationView lottieAnimationView) {

        if(lottieAnimationView != null){
            lottieAnimationView.cancelAnimation();
        }
    }

    private void cancenAnimation(){
        cancenAnimation(mOneAnimation);
        cancenAnimation(mTwoAnimation);
        cancenAnimation(mThreeAnimation);
        cancenAnimation(mFourAnimation);
        cancenAnimation(mFiveAnimation);
        cancenAnimation(mSixAnimation);
        cancenAnimation(mSevenAnimation);
        cancenAnimation(mEightAnimation);
        cancenAnimation(mNineAnimation);
        cancenAnimation(mTenAnimation);
    }


}

