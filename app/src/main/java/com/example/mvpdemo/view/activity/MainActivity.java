package com.example.mvpdemo.view.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.example.mvpdemo.R;
import com.example.mvpdemo.base.BaseMvpActivity;
import com.example.mvpdemo.contract.MainContract;
import com.example.mvpdemo.presenter.MainPresenter;
import com.example.mvpdemo.utils.AppManager;
import com.example.mvpdemo.utils.ToastUtils;

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


    private long mExitTime;
    /**
     * 再按一次退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                ToastUtils.showTipMsg(R.string.exit_app);

                mExitTime = System.currentTimeMillis();
            } else {
                mAppManagerLazy.get().finishAllActivity();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
