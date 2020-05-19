package com.cictec.ibd.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.cictec.ibd.base.utils.LogUtil;
import com.cictec.ibd.base.utils.ToastUtil;


/**
 * 基础类 Fragment
 * <p>
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public abstract class BaseFragment extends Fragment {


    /**
     * 判断当前页面是否还存活，再onCreateView中置为true， onDestroyView中置为false
     * <p>
     * 该参数通常再不确定当前Fragment的声明周期中需要操作主线程时进行判断的一个参数
     */
    protected boolean isAlive;


    /**
     * 是否可见
     */
    private boolean isVisibleToUser = false;

    /**
     * view是否已经创建加载完成
     */
    private boolean isViewCreated = false;


    /**
     * 页卡名称
     */
    private String tabName = getUseName();

    /**
     * 返回当前页面的名称，如果不重写，则返回当前页面的文件名称
     *
     * @return 返回当前页面的名称
     */
    public String getUseName() {
        return this.getClass().getSimpleName();
    }


    /**
     * 页卡名称，这个属性默认的当Fragment使用PageAdapter时，嵌套TabLayout时，可通过这个属性设置页卡名称
     *
     * @return 页卡名称
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * 设置当前页卡的名称
     *
     * @param tabName 页卡名称
     */
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    /**
     * 界面状态绑定的view
     */
    public View pageStatusBindView = null;

    /**
     * 初始化父布局View
     *
     * @param inflater           布局加载器
     * @param container          父布局容器
     * @param savedInstanceState 保存的数据，低内存被回收意外关闭的时候会回调相应的方法
     * @return View  容器视图
     */
    public abstract View initRootView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化子布局View
     */
    public void initChildView() {

    }


    /**
     * 初始化View监听器
     */
    public void initListener() {

    }


    public void onMenuClick() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isAlive = true;
        return initRootView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        initChildView();
        initListener();
        if (isViewCreated && isVisibleToUser) {
            onLazyLoadData();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isViewCreated) {
            onLazyLoadData();
        }
    }


    /**
     * 当页面可见时会回调这个方法，可以再这里判断是否需要对数据进行刷新和加载
     * 是否是只加载一次数据需要自行判断
     */
    protected void onLazyLoadData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isAlive = false;
        closedDialog();
    }


    public <V extends View> V getChildView(int resId) {
        View view = getView();
        if (view != null) {
            return view.findViewById(resId);
        } else {
            return null;
        }
    }

    protected void showLongToast(CharSequence c) {
        Context context = getContext();
        if (context != null) {
            ToastUtil.showLongToast(context, c);
        }
    }

    protected void showLongToast(int resId) {
        Context context = getContext();
        if (context != null) {
            ToastUtil.showLongToast(context, resId);
        }
    }

    protected void showShortToast(CharSequence c) {
        Context context = getContext();
        if (context != null) {
            ToastUtil.showShortToast(context, c);
        }
    }

    protected void showShortToast(int resId) {
        Context context = getContext();
        if (context != null) {
            ToastUtil.showShortToast(context, resId);
        }
    }

    /**
     * 创建统一的Dialog
     */
    public void buildDialog() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).buildDialog();
        }

    }

    /**
     * 创建统一的提示Dialog
     *
     * @param title   标题名称
     * @param content 内容
     */
    public void buildDialog(String title, String content) {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).showProgressDialog(title, content);
        }

    }

    /**
     * 关闭当前页面
     */
    public void closedDialog() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).closedDialog();
        }
    }


    /**
     * 案件拦截，再容器Activity中如果按钮点击事件发生之后，会对此方法进行回调。
     *
     * @param keyCode 按键Code
     * @param event   事件
     * @return 处理结果
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    protected void finishThis() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).finishThis();
        } else {
            Activity activity = getActivity();
            if (activity != null) {
                activity.finish();
            }
        }
    }


    /**
     * 运行一个线程进行访问
     *
     * @param runnable 待执行得任务
     */
    protected void post(Runnable runnable) {
        View view = getView();
        if (view != null) {
            view.post(runnable);
        }
    }

    /**
     * 延迟执行一个线程活动
     *
     * @param runnable    待执行得任务
     * @param delayMillis 延迟得毫秒数
     */
    protected void postDelayed(Runnable runnable, long delayMillis) {
        View view = getView();
        if (view != null) {
            view.postDelayed(runnable, delayMillis);
        }
    }

    /**
     * 移除一个线程活动
     *
     * @param action 需要移除得线程任务
     */
    protected void removeCallback(Runnable action) {
        View view = getView();
        if (view != null) {
            view.removeCallbacks(action);
        }
    }


}
