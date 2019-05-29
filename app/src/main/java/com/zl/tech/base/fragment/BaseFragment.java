package com.zl.tech.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle3.components.support.RxFragment;
import com.zl.tech.base.BaseView;
import com.zl.tech.base.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;

/**
 * Created by zhanglu on 2019/03/03   QQ:1228717266
 *
 * <p/>
 *
 * BaseFragment   Fragment 的Base类
 */
public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements BaseView {

    private Unbinder unbinder;

    @Inject
    protected P mPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(protetedId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



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

    protected abstract void initData();

}
