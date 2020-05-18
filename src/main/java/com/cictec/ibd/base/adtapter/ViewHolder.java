package com.cictec.ibd.base.adtapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-10-24
 * @version   1.0
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    /**
     * 
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param context
     * @param parent
     * @param layoutId
     * @param position
     */
    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);

    }

    /**
     * 获取ViewHolder
     * 
     * @param context
     * @param parent
     * @param convertView
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, ViewGroup parent,
                                 View convertView, int layoutId, int position) {
        if (null == convertView) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }

    }

    /**
     * 获取布局中的控件View
     * 
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    /**
     * 
     * <p>
     * Title: getPosition
     * </p>
     * <p>
     * Description:获取当前位置
     * </p>
     * 
     * @return
     */
    public int getPosition() {
        return mPosition;
    }

    /**
     * 
     * 
     * <p>
     * Title: getConvertView
     * </p>
     * <p>
     * Description: 获取缓存View
     * </p>
     * 
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 
     * <p>
     * Title: setText
     * </p>
     * <p>
     * Description: TextView 设置文本内容
     * </p>
     * 
     * @param viewId
     * @param c
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence c) {
        TextView tv = getView(viewId);
        tv.setText(c);
        return this;
    }

    /**
     * 
     * <p>
     * Title: setText
     * </p>
     * <p>
     * Description: TextView 设置文本内容
     * </p>
     * 
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setText(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setText(resId);
        return this;
    }

    /**
     * 
     * <p>
     * Title: setChecked
     * </p>
     * <p>
     * Description: 设置CheckBox 是否选中
     * </p>
     * 
     * @param viewId
     * @param isChecked
     * @return
     */
    public ViewHolder setChecked(int viewId, boolean isChecked) {
        CheckBox cb = getView(viewId);
        cb.setChecked(isChecked);
        return this;
    }

    /**
     * 
     * <p>
     * Title: setImageRes
     * </p>
     * <p>
     * Description:设置ImageView图片
     * </p>
     * 
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageRes(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * 
     * <p>
     * Title: setImageRes
     * </p>
     * <p>
     * Description: 设置ImageView图片
     * </p>
     * 
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageRes(int viewId, Bitmap bm) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bm);
        return this;
    }

    /**
     * 
     * <p>
     * Title: setImageRes
     * </p>
     * <p>
     * Description: 设置ImageView图片
     * </p>
     * 
     * @param viewId
     * @param drawable
     * @return
     */
    public ViewHolder setImageRes(int viewId, Drawable drawable) {
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

}
