package com.cictec.ibd.base.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.cictec.ibd.base.base.BaseFragment;
import com.hankun.libnavannotation.FragmentDestination;


/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * <p>
 * 当出现界面异常无法正常加载时进行创建，避免无信息提示时不友好的界面
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
@FragmentDestination(pageUrl = "ErrorInfoFragment")
public class ErrorInfoFragment extends BaseFragment {


    @Override
    public String getTabName() {
        return "页面出错";
    }

    @Override
    public View initRootView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("页面出错");
        textView.setTextSize(32);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    @Override
    public void initChildView() {

    }

    @Override
    public void initListener() {

    }


}
