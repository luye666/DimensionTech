package com.zl.tech.presenter.fragment;

import com.zl.tech.base.presenter.BasePresenter;
import com.zl.tech.contract.fragment.CommuntContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2019/5/29
 */
public class CommuntPresenter extends BasePresenter<CommuntContract.CommuntView> {

    @Inject
    public CommuntPresenter() {}

    @Override
    public void AttachView(CommuntContract.CommuntView communtView) {
        super.AttachView(communtView);


    }
}
