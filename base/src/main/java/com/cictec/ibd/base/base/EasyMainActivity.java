package com.cictec.ibd.base.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cictec.ibd.base.R;
import com.cictec.ibd.base.config.ConfigKt;
import com.cictec.ibd.base.fragment.ErrorInfoFragment;


/**
 * 处理加载界面的一个通用界面
 * <p>
 * CopyRight (c)2015: <北京中航讯科技股份有限公司 >
 *
 * @author Hankun
 * @date 2016/3/22
 */
public class EasyMainActivity extends BaseActivity {

    /**
     * 当前加载的Fragment
     */
    private BaseFragment fragment = null;

    /**
     * 加载Fragment的容器
     */
    private FrameLayout root = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        setContentView(R.layout.base_activity_common);
        boolean flag = getIntent().getBooleanExtra(ConfigKt.SHOW_TITLE, true);
        if (flag) {
            initToolbar();
        } else {
            //隐藏Toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setVisibility(View.GONE);
            root = findViewById(R.id.container);
            DisplayMetrics out = new DisplayMetrics();
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(out);
            root.setLayoutParams(new CoordinatorLayout.LayoutParams(out.widthPixels, out.heightPixels));
        }
        initFragment();
    }


    /**
     * 对Toolbar进行初始化，将Toolbar绑定到ActionBar，替换原有的ActionBar来进行操作
     */
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        String title = getIntent().getStringExtra(ConfigKt.TITLE);
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 加载指定的Fragment，对于其Fragment进行反射加载
     */
    private void initFragment() {
        Bundle bundle = getIntent().getExtras();
        try {
            if (bundle != null) {
                String className = bundle.getString(ConfigKt.CLASSNAME, ErrorInfoFragment.class.getName());
                Class c = Class.forName(className);
                fragment = (BaseFragment) c.newInstance();
            } else {
                fragment = new ErrorInfoFragment();
            }
        } catch (Exception ig) {
            fragment = new ErrorInfoFragment();
        }
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }


    @Override
    public void initListener() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishThis();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragment.onKeyDown(keyCode, event)) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishThis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
