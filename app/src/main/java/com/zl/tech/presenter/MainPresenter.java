package com.zl.tech.presenter;
import com.zl.tech.base.presenter.BasePresenter;
import com.zl.tech.contract.MainContract;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by Administrator on 2019/5/29
 */
public class MainPresenter extends BasePresenter<MainContract.MainView> implements MainContract.IMainPresenter {

    @Inject
    public MainPresenter() {
    }

    /**
     * 延迟两秒自动进入主页面
     * @param mainView
     */
    @Override
    public void AttachView(MainContract.MainView mainView) {
        super.AttachView(mainView);

        long splashTime = 2000;
        addSubscribe(Observable.timer(splashTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> mainView.goToShow()));
    }
}
