package com.zl.tech.base.presenter;

import com.zl.tech.base.BaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhanglu on 2019/05/28   QQ:1228717266
 * <p/>
 *
 *
 */
public abstract class BasePresenter<V extends BaseView> implements AbstractSimplePresenter<V>{

    private WeakReference<V> weakReference;

    /**
     * CompositeDisposable:可以快速解除所有添加的Disposable类
     *  每当我们得到一个Disposable时就调用CompositeDisposable.add()将它添加到容器中,
     *  在退出的时候, 调用CompositeDisposable.clear()
     *  即可快速解除.
     */
    private CompositeDisposable compositeDisposable;

    /**
     * 注入Activity，创建弱引用解决内存泄露的隐患
     * @param v   BaseView的子类
     */
    @Override
    public void AttachView(V v) {
        weakReference = new WeakReference(v);
        v = null;
    }

    public V getView() {
        return weakReference.get();
    }

    boolean isAttachView() {
        return weakReference.get() != null && weakReference != null;
    }

    /**
     * 在View销毁的时候调用
     * 解除所有的订阅以及解除V与P层的关系
     */
    @Override
    public void Destory() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }

        if (isAttachView()) {
            weakReference.clear();
            weakReference = null;
        }
    }


    @Override
    public void showLoading() {

    }

    /**
     * 创建CompositeDisposable，并添加Disposable
     * @param disposable：查询以及解除订阅的类
     */
    protected void addSubscribe(Disposable disposable){
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(disposable);
    }
}