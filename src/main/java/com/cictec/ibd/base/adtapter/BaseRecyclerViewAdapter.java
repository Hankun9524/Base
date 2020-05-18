package com.cictec.ibd.base.adtapter;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected ArrayList<T> data;

    private RecyclerViewBindDataInterface<T> mOnBindDataInterface;
    private RecyclerViewMultiTypeBindDataInterface<T> mOnMultiTypeBindDataInterface;

    public ArrayList<T> getData() {
        return data;
    }

    public BaseRecyclerViewAdapter(RecyclerViewBindDataInterface<T> bindInterface) {
        this(bindInterface, null);
    }

    public BaseRecyclerViewAdapter(RecyclerViewBindDataInterface<T> mOnBindDataInterface, RecyclerViewMultiTypeBindDataInterface<T> mOnMultiTypeBindDataInterface) {
        data = new ArrayList<>();
        this.mOnBindDataInterface = mOnBindDataInterface;
        this.mOnMultiTypeBindDataInterface = mOnMultiTypeBindDataInterface;
    }

    public void setNewData(List<T> src) {
        data.clear();
        addNewData(src);
    }

    public void addNewData(List<T> src) {
        if (src != null) {
            data.addAll(src);
        }
        notifyDataSetChanged();
    }

    public void updateData(int position, T t) {
        if (position >= 0 && position < data.size() && null != t) {
            data.set(position, t);
            notifyItemChanged(position);
        }
    }

    /**
     * 刷新数据
     *
     * @param position
     * @param t
     */
    public void upData(int position, T t) {
        if (position >= 0 && position < data.size() && null != t) {
            data.set(position, t);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mOnMultiTypeBindDataInterface == null) {
            return super.getItemViewType(position);
        } else {
            return mOnMultiTypeBindDataInterface.getViewType(position);
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mOnBindDataInterface.getItemLayoutId(viewType);
        BaseRecyclerViewHolder holder = BaseRecyclerViewHolder.getViewHolder(parent, layoutId);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        mOnBindDataInterface.onBindData(data.get(position), holder, position, getItemViewType(position), data.size());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
