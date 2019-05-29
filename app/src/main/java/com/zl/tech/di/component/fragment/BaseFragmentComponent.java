package com.zl.tech.di.component.fragment;

import com.trello.rxlifecycle3.components.support.RxFragment;
import com.zl.tech.di.annotation.FragmentScope;
import com.zl.tech.di.module.fragment.FragmentMoudel;
import dagger.Component;

/**
 * Created by Administrator on 2019/5/29
 */
@FragmentScope
@Component(modules = FragmentMoudel.class)
public interface BaseFragmentComponent {

    void inject(RxFragment fragment);
}
