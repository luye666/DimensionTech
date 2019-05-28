package com.zl.tech.mvp.base;

import java.lang.ref.WeakReference;

import javax.inject.Inject;


/**
 * @author Administrator QQ:1228717266
 * @name DimensionTech
 * @time 2018/11/29 15:31
 */
public abstract class BasePresenter<P extends BaseView> {
    @Inject
    private WeakReference<P> weakReference;

    public P getView() {
        return weakReference.get();
    }

    boolean isAttachView() {
        return weakReference.get() != null && weakReference != null;
    }

    public void AttachView(P p) {

        weakReference = new WeakReference(p);
        p = null;
    }

    public void Destory() {
        if (isAttachView()) {
            weakReference.clear();
            weakReference = null;
        }
    }


}