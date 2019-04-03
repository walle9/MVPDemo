package com.example.mvpdemo.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.example.mvpdemo.base.MyApplication;

/**
 * @date create at 2019/3/28 11:47
 * @describe 描述:实时更新的Toast工具类
 */
public class ToastUtils {
    private static Toast toast;

    private ToastUtils() {
        throw new RuntimeException("工具类不允许创建对象");
    }

    @SuppressWarnings("all")
    private static void init() {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), "", Toast.LENGTH_SHORT);
        }
    }

    public static void showTipMsg(String msg) {
        if (null == toast) {
            init();
        }
        toast.setText(msg);
        toast.show();
    }

    public static void showTipMsg(@StringRes int msg) {
        if (null == toast) {
            init();
        }
        toast.setText(msg);
        toast.show();
    }
}
