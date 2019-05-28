package com.zl.tech.mvp.model.net;
import android.content.Context;
import android.util.Log;

import androidx.core.widget.ContentLoadingProgressBar;

import com.zl.tech.mvp.base.BaseResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLHandshakeException;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author Administrator QQ:1228717266
 * @name DimensionTech
 * @time 2018/11/29 20:09
 */
public class OnSuccessAndFaultSub<T> extends DisposableObserver<T> implements ProgressCancelListener{
    private ContentLoadingProgressBar progressDialog;
    private boolean showProgress = true;
    private OnSuccessAndFaultListener mOnSuccessAndFaultListener;
    private Context context;

    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
    }

    /**
     * @param mOnSuccessAndFaultListener 成功回调监听
     * @param context                    上下文
     */
    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener, Context context) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
        this.context = context;
        progressDialog = new ContentLoadingProgressBar(context);
    }

    /**
     * @param mOnSuccessAndFaultListener 成功回调监听
     * @param context                    上下文
     * @param showProgress               是否需要显示默认Loading
     */
    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener, Context context, boolean showProgress) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
        this.context = context;
        progressDialog = new ContentLoadingProgressBar(context);
        this.showProgress = showProgress;
    }



    @Override
    protected void onStart() {
        super.onStart();

        showProgressDialog();
    }


    @Override
    public void onNext(T t) {

        if (t == null){mOnSuccessAndFaultListener.onFault("数据为空");
        }else {

            if (t instanceof BaseResponse && ((BaseResponse) t).isSuccess()){
                if (((BaseResponse) t).result != null) {
                    mOnSuccessAndFaultListener.onSuccess(((BaseResponse) t).result);
                }else {
                    mOnSuccessAndFaultListener.onSuccess(((BaseResponse) t).getMessage());
                }
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        try {

            if (e instanceof SocketTimeoutException) {//请求超时
            } else if (e instanceof ConnectException) {//网络连接超时
                mOnSuccessAndFaultListener.onFault("网络连接超时");
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                mOnSuccessAndFaultListener.onFault("安全证书异常");
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    mOnSuccessAndFaultListener.onFault("网络异常，请检查您的网络状态");
                } else if (code == 404) {
                    mOnSuccessAndFaultListener.onFault("请求的地址不存在");
                } else {
                    mOnSuccessAndFaultListener.onFault("请求失败");
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
                mOnSuccessAndFaultListener.onFault("域名解析失败");
            } else {
                mOnSuccessAndFaultListener.onFault("error:" + e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
            dismissProgressDialog();
            progressDialog = null;

        }
        
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
        progressDialog = null;
    }

    private void dismissProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.hide();
        }

    }

    // TODO: 2018/11/29 显示进度条
    private void showProgressDialog() {
    
        if (showProgress && null != progressDialog) {
            progressDialog.show();
        }
    }

    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }
}
