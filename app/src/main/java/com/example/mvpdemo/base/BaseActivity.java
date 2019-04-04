package com.example.mvpdemo.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mvpdemo.R;
import com.example.mvpdemo.di.component.ActivityComponent;
import com.example.mvpdemo.di.component.DaggerActivityComponent;
import com.example.mvpdemo.di.module.ActivityModule;
import com.example.mvpdemo.utils.AppManager;
import com.example.mvpdemo.utils.ToastUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import javax.inject.Inject;

import dagger.Lazy;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @date create at 2019/3/28 10:46
 * @describe 描述:所有Activity的父类,不适用mvp模式的activity可直接继承本类
 */
public abstract class BaseActivity extends SupportActivity implements BaseView, View.OnClickListener {

    private android.app.AlertDialog loadingDialog;
    private Toolbar toolbar;
    private TextView tvToolbarTitle;
    private TextView tvToolbarRight;
    private TextView tvBack;
    private View lineHorizontal;
    @Inject
    public Lazy<AppManager> mAppManagerLazy;

    private DialogInterface.OnKeyListener onKeyListener =
            (dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //设置是否侵入,默认不侵入
        if (setStatusBar()) {
            initBar();
        }

        //设置是否强制竖屏,默认强制
        if (setOrientationPortrait()) {
            //强制竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        //设置布局
        int layoutId = getLayoutId(savedInstanceState);//获取当前布局id
        View inflate = getLayoutInflater().inflate(R.layout.activity_base, toolbar, false);

        //没有布局的时候传0
        if (0 == layoutId) {
            //如果没有设置布局,就加载默认布局
            setContentView(inflate);
        } else {
            //如果设置了布局,就把设置的布局添加到默认布局中
            LinearLayout rootLinearLayout = inflate.findViewById(R.id.ll_layout_base_activity);
            View rootView = getLayoutInflater().inflate(layoutId, rootLinearLayout, true);
            setContentView(rootView);
        }

        //初始化控件
        initView();

        //activity添加到管理
        mAppManagerLazy.get().addActivity(this);

        //初始化数据
        initData();

        //设置点击事件
        setOnClick(R.id.tv_back_base_activity);

    }


    /**
     * 初始化布局的方法,交由子类实现
     */
    protected void initView() {
        toolbar = findViewById(R.id.toolbar_base_activity);
        tvToolbarTitle = findViewById(R.id.tv_title_base_activity);
        tvToolbarRight = findViewById(R.id.tv_right_base_activity);
        lineHorizontal = findViewById(R.id.view_base_activity);
    }


    /**
     * 加载数据的方法,交由子类实现
     */
    protected void initData() {
    }


    /**
     * 获取当前布局对象,交由子类实现
     *
     * @param savedInstanceState 这个是当前activity保存的数据，最常见的就是横竖屏切换的时候，
     *                           数据丢失问题
     * @return 当前布局的int值
     */
    protected abstract int getLayoutId(Bundle savedInstanceState);


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back_base_activity:
                onBackPressedSupport();
                break;
            default:
                break;
        }
    }


    /**
     * Toast 提示用户
     *
     * @param msg 提示内容String
     */
    @Override
    public void showTipMsg(String msg) {
        ToastUtils.showTipMsg(msg);
    }


    /**
     * Toast 提示用户
     *
     * @param msg 提示内容res目录下面的String的int值
     */
    @Override
    public void showTipMsg(int msg) {
        ToastUtils.showTipMsg(msg);
    }


    @Override
    public void invalidToken() {
        //用于检测你当前用户的token是否有效，无效就返回登录界面，具体的业务逻辑你自己实现
        //如果需要做到实时检测，推荐用socket长连接，每隔10秒发送一个验证当前登录用户token是否过期的请求
    }


    /**
     * 网络请求的时候显示正在加载的对话框
     */
    @Override
    public void showLoading() {
        if (null == loadingDialog) {
            loadingDialog = new AlertDialog.Builder(this).setView(new ProgressBar(this))
                    .setCancelable(false).setOnKeyListener(onKeyListener).create();
            loadingDialog.setCanceledOnTouchOutside(false);
            Window window = loadingDialog.getWindow();
            if (null != window) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }


    /**
     * 网络请求完成时隐藏加载对话框
     */
    @Override
    public void hideLoading() {
        if (null != loadingDialog) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        }
    }


    /**
     * Finish当前页面，最好实现onBackPressedSupport()，这个方法会有一个退栈操作，
     * 开源框架实现的，我们不用管
     */
    @Override
    public void myFinish() {
        onBackPressedSupport();
    }


    /**
     * 设置Fragment的转场动画,子类可重写此方法改变转场动画
     *
     * @return
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }


    @Override
    protected void onDestroy() {
        mAppManagerLazy.get().finishActivity(this);
        super.onDestroy();
    }


    /**
     * 设置是否入侵状态栏,默认不侵入
     * 子类可以重写此方法设置是否侵入状态栏
     *
     * @return true 侵入
     */
    public boolean setStatusBar() {
        return false;
    }

    /**
     * 设置是否强制竖屏,默认强制
     * 子类可以重写此方法设置是否侵入状态栏
     *
     * @return true 强制
     */
    public boolean setOrientationPortrait() {
        return true;
    }


    /**
     * 初始化toolbar的内容,子类使用该方法修改toolbar
     *
     * @param isShowToolbar        是否显示toolbar
     * @param isShowBack           是否显示左边的TextView
     * @param isShowMore           是否显示右边的TextView
     * @param isShowLineHorizontal 是否显示显示水平线
     * @return 当前activity对象，可以连点
     */
    protected BaseActivity initToolbar(boolean isShowToolbar, boolean isShowBack,
                                       boolean isShowMore, boolean isShowLineHorizontal) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            if (isShowToolbar) {
                actionBar.show();
                tvBack = findViewById(R.id.tv_back_base_activity);
                TextView textView = findViewById(R.id.tv_right_base_activity);
                if (null != tvBack && null != textView) {
                    tvBack.setVisibility(isShowBack ? View.VISIBLE : View.INVISIBLE);
                    textView.setVisibility(isShowMore ? View.VISIBLE : View.INVISIBLE);
                }
            } else {
                actionBar.hide();
            }
        }
        if (!isShowLineHorizontal) {
            lineHorizontal.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    @SuppressWarnings("unused")
    public BaseActivity setMyTitle(String title) {
        tvToolbarTitle.setText(title);
        return this;
    }


    /**
     * 设置标题
     *
     * @param stringId
     * @return
     */
    public BaseActivity setMyTitle(@StringRes int stringId) {
        tvToolbarTitle.setText(stringId);
        return this;
    }


    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public BaseActivity setTitles(CharSequence title) {
        tvToolbarTitle.setText(title);
        return this;
    }


    /**
     * 设置Toolbar背景颜色
     *
     * @param colorId
     * @return
     */
    public BaseActivity setToolbarBack(int colorId) {
        toolbar.setBackgroundColor(getResources().getColor(colorId));
        return this;
    }


    /**
     * 设置左边内容.同时清空左边背景
     *
     * @param leftTitle 内容
     * @return {@link BaseActivity}
     */
    public BaseActivity setLeftTitle(String leftTitle) {
        if (tvBack != null) {
            tvBack.setBackground(null);
            tvBack.setText(leftTitle);
        }
        return this;
    }


    /**
     * 设置左边内容.同时清空左边背景
     *
     * @param leftTitle 内容
     */
    public void setLeftTitle(@StringRes int leftTitle) {
        if (tvBack != null) {
            tvBack.setBackground(null);
            tvBack.setText(leftTitle);
        }
    }


    /**
     * 设置左边的背景
     *
     * @param resId
     * @return
     */
    @SuppressWarnings("unused")
    protected BaseActivity setLeftBackground(int resId) {
        tvBack.setBackgroundResource(resId);
        return this;
    }


    /**
     * 设置标题右边更多的内容
     *
     * @param moreTitle
     */
    public void setMoreTitle(String moreTitle) {
        tvToolbarRight.setText(moreTitle);
    }


    /**
     * 设置标题右边更多的内容
     *
     * @param stringId
     * @return
     */
    public BaseActivity setMoreTitle(@StringRes int stringId) {
        tvToolbarRight.setText(stringId);
        return this;
    }


    /**
     * 设置右边更多的背景,默认没有
     *
     * @param resId
     * @return
     */
    @SuppressWarnings("unused")
    protected BaseActivity setMoreBackground(int resId) {
        tvToolbarRight.setBackgroundResource(resId);
        return this;
    }


    /**
     * 设置状态栏背景颜色，不能改变状态栏内容的颜色
     */
    private void stateBar(String color) {
        if (!TextUtils.isEmpty(color)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setTintColor(Color.parseColor(color));

        }
    }


    /**
     * 设置点击事件.
     *
     * @param ids 被点击View的ID
     * @return {@link BaseActivity}
     */
    public BaseActivity setOnClick(@IdRes int... ids) {
        View view;
        for (int id : ids) {
            view = findViewById(id);
            if (null != view) {
                view.setOnClickListener(this);
            }
        }
        return this;
    }


    /**
     * 设置点击事件.
     *
     * @param views 被点击View
     * @return {@link BaseActivity}
     */
    public BaseActivity setOnClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
        return this;
    }


    /**
     * 设置透明状态栏
     */
    protected void initBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            //获取顶级窗口
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN      //全屏标志,布局侵入
                    //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION    //标志布局会侵入到导航栏下
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;            //保持稳定
            decorView.setSystemUiVisibility(option);                //设置系统UI可见属性

            getWindow().setStatusBarColor(Color.TRANSPARENT);       //设置状态栏颜色透明
            // getWindow().setNavigationBarColor(Color.TRANSPARENT);   //设置导航栏颜色透明

            //设置状态栏为半透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置导航栏为半透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    localLayoutParams.flags);
        }

    }


    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule())
                .build();
    }


}
