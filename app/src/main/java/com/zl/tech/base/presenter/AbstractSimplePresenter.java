package com.zl.tech.base.presenter;

/**
 * Created by Administrator on 2019/5/29
 *
 * <p/>
 * Presenter的基类：拥有所有行为
 *
 */
public interface AbstractSimplePresenter<V> {

    /**
     * 注入View
     */
    void AttachView(V v);

    /**
     * P和V的解绑
     */
    void Destory();


    /**
     * 显示进度条
     */
    void showLoading();


}
