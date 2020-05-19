package com.cictec.ibd.base.adtapter;



public abstract class AbstractAsyncListDifferBindData<T> implements AbstractRecyclerViewBindDataInterface<T> {

    @Override
    public Boolean areItemsTheSame(T oldItem, T newItem) {
        return true;
    }

    @Override
    public Boolean areContentsTheSame(T oldItem, T newItem) {
        return false;
    }
}
