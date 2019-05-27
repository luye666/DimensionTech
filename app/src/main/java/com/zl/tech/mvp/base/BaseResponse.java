package com.zl.tech.mvp.base;

import android.text.TextUtils;

/**
 * 基类的封装
 *
 * @author Administrator QQ:1228717266
 * @name DimensionTech
 * @time 2018/11/29 19:46
 */
public class BaseResponse<T> {


    private String status;
    private String message;
    public T result;

    public boolean isSuccess(){
        return TextUtils.equals(status,"0000");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
