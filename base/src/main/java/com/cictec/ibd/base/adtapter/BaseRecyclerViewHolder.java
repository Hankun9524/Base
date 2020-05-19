package com.cictec.ibd.base.adtapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public class BaseRecyclerViewHolder extends ViewHolder {

    /**
     * 子View的集合
     */
    private SparseArray<View> childView;

    /**
     * 根View
     */
    private View contentView;

    public View getContentView() {
        return contentView;
    }

    public static BaseRecyclerViewHolder getViewHolder(ViewGroup parent, int layoutId) {
        return new BaseRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }


    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        childView = new SparseArray<>();
        contentView = itemView;
    }


    /**
     * 获取子View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getSubView(int viewId) {
        View view = childView.get(viewId);
        if (view == null) {
            view = contentView.findViewById(viewId);
            childView.put(viewId, view);
        }
        return (T) view;
    }


}
