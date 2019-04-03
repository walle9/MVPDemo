package com.example.mvpdemo.view.activity;

import android.os.Bundle;

import com.example.mvpdemo.R;
import com.example.mvpdemo.base.BaseMvpActivity;
import com.example.mvpdemo.contract.MainContract;
import com.example.mvpdemo.presenter.MainPresenter;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.IView {

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        initToolbar(false, false, false,false);
        basePresenter.loadData();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
