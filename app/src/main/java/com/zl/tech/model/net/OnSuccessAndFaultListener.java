package com.zl.tech.model.net;

public interface OnSuccessAndFaultListener<T> {
    void onSuccess(T result);

    void onFault(String errorMsg);
}
