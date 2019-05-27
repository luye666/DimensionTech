package com.zl.tech.mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhanglu on 2019/03/03   QQ:1228717266
 *
 * <p/>
 *
 * BaseFragment   Fragment 的Base类
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements BaseView {

    private Unbinder unbinder;
    protected P mPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(protetedId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mPresenter == null) {
            mPresenter = initPresenter();
        }

        if (mPresenter != null) {
            mPresenter.AttachView(this);
        }

        initData();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mPresenter == null) {
                return;
            }
        }
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        if (mPresenter.isAttachView()) {
            mPresenter.Destory();
        }
    }

    protected abstract int protetedId();

    protected abstract P initPresenter();

    protected abstract void initData();

}
