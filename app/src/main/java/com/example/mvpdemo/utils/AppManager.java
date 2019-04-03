package com.example.mvpdemo.utils;

import android.app.Activity;

import com.example.mvpdemo.view.activity.MainActivity;

import java.util.Stack;

import javax.inject.Inject;

/**
 * Description:
 * Data：2018/9/25-15:47
 */
public class AppManager {
    private  Stack<Activity> activityStack;


    @Inject
    public AppManager() {
    }



    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    //MainActivity是否在栈中
    public boolean isMainActivityAlive() {

        for (Activity activity : activityStack) {
            if (activity.getClass().equals(MainActivity.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


//    public void AppExit(Context context) {
//        try {
//            finishAllActivity();
//            ActivityManager activityMgr =
//                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            activityMgr.killBackgroundProcesses(context.getPackageName());
//            System.exit(0);
//        } catch (Exception e) {
//        }
//    }

    public boolean isAppExit() {
        return activityStack == null || activityStack.isEmpty();
    }
}