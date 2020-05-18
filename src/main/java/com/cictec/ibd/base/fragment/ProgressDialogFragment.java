package com.cictec.ibd.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cictec.ibd.base.R;


/**
 * @author Hankun
 * <p>
 * 弹出对话框的样式
 * <p>
 * Created by HanKun on 2016/12/8.
 */

public class ProgressDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去除标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.base_dialog_progress, container);
        TextView titleTv = view.findViewById(R.id.tv_base_progress_title);
        TextView msgTv = view.findViewById(R.id.tv_base_progress_msg);
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String msg = bundle.getString("msg");
        titleTv.setText(title);
        msgTv.setText(msg);
        //点击外部不消失
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }


    /**
     * 直接静态获取一个实例的方法
     *
     * @param title 标题
     * @param msg   内容
     * @return 返回组建好的Dialog
     */
    public static ProgressDialogFragment newInstance(String title, String msg) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("msg", msg);
        fragment.setArguments(bundle);
        return fragment;
    }


}
