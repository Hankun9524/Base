package com.cictec.ibd.base.adtapter;

import android.view.View;

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public interface RecyclerViewOnItemClickListener {

    /**
     * 单次点击
     *
     * @param view     点击的view
     * @param position 点击位置
     */
    void onItemClick(View view, int position);

    /**
     * 长按点击
     *
     * @param view     点击的View
     * @param position 点击的位置
     */
    void onItemLongClick(View view, int position);

}
