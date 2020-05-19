package com.cictec.ibd.base.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.cictec.ibd.base.R;
import com.cictec.ibd.base.fragment.ProgressDialogFragment;
import com.cictec.ibd.base.utils.ToastUtil;

import java.util.List;


/**
 * 基础类Activity
 * <p>
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public abstract class BaseActivity extends AppCompatActivity {


    /**
     * 返回当前页面的名称，如果不重写，则返回当前页面的文件名称
     *
     * @return 返回当前页面的名称
     */
    public String getUseName() {
        return this.getClass().getSimpleName();
    }


    /**
     * 通用的等待框
     */
    private ProgressDialogFragment dialogFragment;


    /**
     * 判断当前页面是否还存活，再onCreate中置为true， onDestroy中置为false
     * <p>
     * 该参数通常再不确定当前Activity的声明周期中需要操作主线程时进行判断的一个参数
     */
    protected boolean isAlive;

    /**
     * 判断当前页面是否再显示的状态，OnResume时为true，OnPause时为false
     */
    protected boolean isShow;

    /**
     * 打开提示框时的时间，设置这个参数是由于再部分系统中，创建的速度比较慢，但是出现了迅速退出时需要关闭之前打开的对话框，
     * 由于还未创建完成再次关闭的时候会出现一个异常报错，因此再这里记录上次打开的时间，用来做一个延时的关闭操作，如果关闭的
     * 操作比打开的操作大100ms，则允许进行关闭的操作。如果小于100ms，进行一个延迟的关闭操作指令。
     */
    private long openTime;
    /**
     * 是否处于后台
     */
    protected boolean isBackstage;


    /**
     * 快捷打开等待框
     * <p>
     * 默认title：”提示“
     * 默认内容msg：”请稍候“
     */
    public void buildDialog() {
        showProgressDialog("提示", "请稍候");
    }

    /**
     * 关闭等待对话框
     */
    public void closedDialog() {
        dismissProgressDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBackstage = true;
        initView();
        initListener();
        isAlive = true;
    }

    /**
     * 初始化View
     */
    public void initView() {

    }

    /**
     * 初始化监听器
     */
    public void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //进入显示状态
        isShow = true;
        //当前App从后台切换到前台
        if (!isBackstage) {
            runInStage();
        }
        isBackstage = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭软键盘
        closeSoftInput();
        //当前App从后台切换到前台
        boolean flag = isAppOnForeground();
        if (!flag) {
            runInBackground();
            isBackstage = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
    }

    /**
     * app从后台切入前台
     */
    public void runInStage() {

    }

    /**
     * App切入后台
     */
    public void runInBackground() {

    }


    /**
     * 打开等待对话框
     *
     * @param title 标题
     * @param msg   内容
     */
    public void showProgressDialog(String title, String msg) {
        if (isAlive) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("progressDialog");
            if (fragment != null) {
                ft.remove(fragment);
            }
            dialogFragment = ProgressDialogFragment.newInstance(title, msg);
            ft.add(dialogFragment, "progressDialog")
                    .commitAllowingStateLoss();
            openTime = System.currentTimeMillis();
        }
    }

    /**
     * 关闭等待对话框
     */
    public void dismissProgressDialog() {
        if (isAlive) {
            try {
                long closeTime = System.currentTimeMillis();
                if (closeTime - openTime < 100) {
                    View view = findViewById(android.R.id.content);
                    view.postDelayed(() -> {
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("progressDialog");
                        if (fragment != null && dialogFragment != null && fragment.isVisible()) {
                            dialogFragment.dismissAllowingStateLoss();
                        }
                        dialogFragment = null;
                    }, 100);
                } else {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("progressDialog");
                    if (fragment != null && dialogFragment != null && fragment.isVisible()) {
                        dialogFragment.dismissAllowingStateLoss();
                    }
                    dialogFragment = null;
                }
            } catch (Exception ignored) {
            }
        }
    }


    protected void showLongToast(CharSequence c) {
        ToastUtil.showLongToast(getApplicationContext(), c);
    }

    protected void showLongToast(int resId) {
        ToastUtil.showLongToast(getApplicationContext(), resId);
    }

    protected void showShortToast(CharSequence c) {
        ToastUtil.showShortToast(getApplicationContext(), c);
    }

    protected void showShortToast(int resId) {
        ToastUtil.showShortToast(getApplicationContext(), resId);
    }


    /**
     * 关闭软键盘
     */
    public void closeSoftInput() {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (manager.isActive() && getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public Resources getResources() {
        //在这里重写这个方法，是为了保持字体不跟随系统的字体设置改变大小。部分系统字体设置变大后会导致界面样式严重失调
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    public boolean isAppOnForeground() {
        // 首先获取activity管理器和当前进程的包名
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        String packageName = getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        } else {
            // 遍历当前运行的所有进程，查看本进程是否是前台进程，如果不是就代表当前应用进入了后台，执行相关关闭操作
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                // The name of the process that this object is associated with.
                if (appProcess.processName.equals(packageName)
                        && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (null == intent) {
            intent = new Intent();
        }
        super.startActivityForResult(intent, requestCode);
    }

    public void finishThis() {
        finish();
        overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
    }

}
