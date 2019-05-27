package com.zl.tech.mvp.base;

import android.content.Context;

import com.zl.tech.mvp.model.api.ApiService;
import com.zl.tech.mvp.model.net.NetManager;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.ResponseBody;

/**
 * @author Administrator QQ:1228717266
 * @name DimensionTech
 * @time 2018/11/29 21:13
 */
public class BaseModel<T> {

    public void subscribe(Context context, Observable observable, ObservableTransformer<T,T> transformer,boolean isDialog, boolean cancelable){
        ApiService apiService = NetManager.getInstance().apiService;


    }


}
