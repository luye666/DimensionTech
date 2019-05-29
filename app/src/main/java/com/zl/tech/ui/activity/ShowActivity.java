package com.zl.tech.ui.activity;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.zl.tech.R;
import com.zl.tech.base.activity.BaseActivity;
import com.zl.tech.ui.fragment.CommunityFragment;
import com.zl.tech.ui.fragment.InformationFlagment;
import com.zl.tech.ui.fragment.MessageFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShowActivity extends BaseActivity {
    private static final String INFORMATION = "information";
    private static final String MESSAGE = "message";
    private static final String COMMUNITY = "community";
    @BindView(R.id.qie_fragment)
    FrameLayout qieFragment;
    @BindView(R.id.btn_rb1)
    RadioButton btnRb1;
    @BindView(R.id.btn_rb2)
    RadioButton btnRb2;
    @BindView(R.id.btn_rb3)
    RadioButton btnRb3;
    @BindView(R.id.btn_rg)
    RadioGroup btnRg;
    private List<String> list;
    private FragmentManager manager;
    private Fragment currentFragment;

    @Override
    protected int initView() {
        return R.layout.activity_show;
    }

    @Override
    protected void initEventAndData() {
        if (btnRb1.isChecked()){
            addFragment(INFORMATION);
        }
        manager = getSupportFragmentManager();
        list = new ArrayList<>();
        list.add(INFORMATION);
        list.add(MESSAGE);
        list.add(COMMUNITY);

        btnRg.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                default:
                    break;
                case R.id.btn_rb1:
                    addFragment(INFORMATION);
                    break;
                case R.id.btn_rb2:
                    addFragment(MESSAGE);
                    break;
                case R.id.btn_rb3:
                    addFragment(COMMUNITY);
                    break;
            }

        });
    }

    @Override
    protected void initToobar() {

    }


    private void addFragment(String fTag) {
        //判断这个标签是否存在Fragment对象,如果存在则返回，不存在返回null
        Fragment fragment = manager.findFragmentByTag(fTag);
        //如果这个fragment不存于栈中
        FragmentTransaction transaction = manager.beginTransaction();
        if (null == fragment) {
            //初始化Fragment事物
            //根据RaioButton点击的Button传入的tag，实例化，添加显示不同的Fragment
            switch (fTag) {
                case "information":
                    fragment = new InformationFlagment();
                    break;
                case "message":
                    fragment = new MessageFragment();
                    break;
                case "community":
                    fragment = new CommunityFragment();
                    break;
            }

            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }


            transaction.add(R.id.qie_fragment, fragment, fTag);
            transaction.commit();
            currentFragment = fragment;
        } else {
            //如果添加的Fragment已经存在,fragment不为空，直接显示fragment
            transaction = manager.beginTransaction();
            transaction.hide(currentFragment);
            transaction.show(fragment);
            currentFragment = fragment;
            transaction.commitAllowingStateLoss();
        }
    }

}
