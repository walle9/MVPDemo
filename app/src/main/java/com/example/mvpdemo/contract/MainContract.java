package com.example.mvpdemo.contract;

import com.example.mvpdemo.base.BasePresenter;
import com.example.mvpdemo.base.BaseView;

/**
 * @date create at 2019/3/28 15:19
 * @describe 描述:
 */
public interface MainContract {

    interface IView extends BaseView {

    }
    interface Presenter extends BasePresenter<IView> {
        void loadData();
    }

}
