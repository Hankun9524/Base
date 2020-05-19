package com.cictec.ibd.base.adtapter;

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * <p>
 * 抽象的RecyclerView 视图和数据的绑定接口
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public interface AbstractRecyclerViewBindDataInterface<T> {

    /**
     * 绑定view
     *
     * @param bean
     * @param holder
     * @param position
     * @param type
     */
    void onBindData(T bean, BaseRecyclerViewHolder holder, int position, int type);

    /**
     * 设置子View的Id
     *
     * @param viewType
     * @return
     */
    int getItemLayoutId(int viewType);

    Boolean areItemsTheSame(T oldItem, T newItem);
    /**
     * 判断旧数据是否和新数据相等,如果不相等更新本条目，如果相等不更新
     */
    Boolean areContentsTheSame(T oldItem, T newItem);


}
