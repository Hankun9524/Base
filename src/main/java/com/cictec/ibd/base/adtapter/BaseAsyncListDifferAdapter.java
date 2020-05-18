package com.cictec.ibd.base.adtapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class BaseAsyncListDifferAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private AsyncListDiffer<T> mDiffer;
    private AbstractRecyclerViewBindDataInterface<T> mOnBindDataInterface;

    public BaseAsyncListDifferAdapter(AbstractRecyclerViewBindDataInterface<T> mOnBindDataInterface) {
        this.mOnBindDataInterface = mOnBindDataInterface;
        DiffUtil.ItemCallback<T> diffCallback = new DiffUtil.ItemCallback<T>() {

            @Override
            public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
                return mOnBindDataInterface.areItemsTheSame(oldItem, oldItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
                return mOnBindDataInterface.areContentsTheSame(oldItem, newItem);
            }
        };
        mDiffer = new AsyncListDiffer<T>(this, diffCallback);
    }

    public void submitList(List<T> data) {
        mDiffer.submitList(data);
    }

    public void upData(T t, int index) {
        if (null != t && index >= 0 && index <= mDiffer.getCurrentList().size() && mDiffer.getCurrentList().size() > 0) {
            List<T> current = mDiffer.getCurrentList();
            List<T> list = new ArrayList<>();
            list.addAll(current);
            list.set(index, t);
            submitList(list);
        }
    }

    public void addSubmitList(List<T> data) {
        if (null != data && data.size() > 0) {
            List<T> list = new ArrayList<>();
            list.addAll(mDiffer.getCurrentList());
            list.addAll(data);
            mDiffer.submitList(list);
        }
    }

    public List<T> getData() {
        return mDiffer.getCurrentList();
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = mOnBindDataInterface.getItemLayoutId(viewType);
        return BaseRecyclerViewHolder.getViewHolder(parent, layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        mOnBindDataInterface.onBindData(mDiffer.getCurrentList().get(position), holder, position, getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }
}
