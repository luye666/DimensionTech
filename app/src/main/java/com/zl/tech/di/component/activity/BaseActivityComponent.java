package com.zl.tech.di.component.activity;
import com.zl.tech.ui.activity.MainActivity;
import dagger.Component;

/**
 * Created by Administrator on 2019/5/29
 */

@Component
public interface BaseActivityComponent {

   void inject(MainActivity activity);
}
