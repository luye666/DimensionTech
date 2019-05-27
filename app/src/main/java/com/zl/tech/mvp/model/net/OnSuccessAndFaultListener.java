package com.zl.tech.mvp.model.net;

/**
 * @author Administrator QQ:1228717266
 * @name DimensionTech
 * @time 2018/11/29 21:38
 */
public interface OnSuccessAndFaultListener<T> {
    void onSuccess(T result);

    void onFault(String errorMsg);
}
