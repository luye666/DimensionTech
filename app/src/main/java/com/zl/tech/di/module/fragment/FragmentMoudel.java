package com.zl.tech.di.module.fragment;
import android.app.Activity;
import com.trello.rxlifecycle3.components.support.RxFragment;
import com.zl.tech.di.annotation.FragmentScope;
import com.zl.tech.presenter.fragment.CommuntPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2019/5/29
 */

@Module
public class FragmentMoudel {

    private RxFragment fragment;

    public FragmentMoudel(RxFragment fragment) {
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    CommuntPresenter provideCommunityFragment(){
        return new CommuntPresenter();
    }

    @Provides
    Activity compatActivity(){
        return fragment.getActivity();
    }

}
