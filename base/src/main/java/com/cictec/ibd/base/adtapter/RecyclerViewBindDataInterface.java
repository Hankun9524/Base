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
public interface RecyclerViewBindDataInterface<T> {

    /**
     * 绑定view
     *
     * @param bean
     * @param holder
     * @param position
     * @param type
     * @param size
     */
    void onBindData(T bean, BaseRecyclerViewHolder holder, int position, int type, int size);

    /**
     * 设置子View的Id
     *
     * @param viewType
     * @return
     */
    int getItemLayoutId(int viewType);

}
