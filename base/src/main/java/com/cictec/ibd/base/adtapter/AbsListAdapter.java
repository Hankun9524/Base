package com.cictec.ibd.base.adtapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * <p>
 * Title: CommonAdapter
 * </p>
 * <p>
 * Description: 通用Adapter适配器
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author Hankun
 * @date 2015年4月19日
 */
public abstract class AbsListAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutResId;

    public AbsListAdapter(Context context, int layoutResId, List<T> datas) {
        this.context = context;
        this.mDatas = datas;
        this.layoutResId = layoutResId;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * 获取当前显示封装的数据集
     *
     * @return
     */
    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 设置新数据
     *
     * @param newData
     */
    public void setNewData(List<T> newData) {
        mDatas = newData;
        this.notifyDataSetChanged();
    }

    /**
     * 设置更多数据，不会移除原有信息
     *
     * @param more
     */
    public void setMoreData(List<T> more) {
        mDatas.addAll(more);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, parent, convertView, layoutResId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);

}
