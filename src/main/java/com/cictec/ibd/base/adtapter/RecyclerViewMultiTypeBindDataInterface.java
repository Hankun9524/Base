package com.cictec.ibd.base.adtapter;

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * <p>
 * 多种类型的RecyclerView的数据实现类接口
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public interface RecyclerViewMultiTypeBindDataInterface<T> extends RecyclerViewBindDataInterface<T> {

    /**
     * 多种子view的绑定
     *
     * @param position
     * @return
     */
    int getViewType(int position);

}
