package com.zl.tech.contract;

/**
 * Created by Administrator on 2019/5/29
 */

import com.zl.tech.base.BaseView;

/**
 * MainActivity与MainPresenter的契约类
 */
public interface MainContract {

    interface MainView extends BaseView {
        void goToShow();
    }

    interface IMainPresenter<T extends MainView>{

    }

}
