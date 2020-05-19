package com.cictec.ibd.base.adtapter;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 *
 * @author HanKun
 * @version 1.0
 * @date 2018-08-23
 */
public class RecyclerViewClickListenerImpl implements RecyclerView.OnItemTouchListener {


    private GestureDetector mGestureDetector;
    private RecyclerViewOnItemClickListener mListener;

    private RecyclerView recyclerView;

    public RecyclerViewClickListenerImpl(RecyclerView recyclerView, RecyclerViewOnItemClickListener mListener) {
        this.mListener = mListener;
        this.recyclerView = recyclerView;

        mGestureDetector = new GestureDetector(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            //单击事件
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View childView = RecyclerViewClickListenerImpl.this.recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && RecyclerViewClickListenerImpl.this.mListener != null) {
                    RecyclerViewClickListenerImpl.this.mListener.onItemClick(childView, RecyclerViewClickListenerImpl.this.recyclerView.getChildLayoutPosition(childView));
                    return true;
                }
                return false;
            }

            //长按事件
            @Override
            public void onLongPress(MotionEvent e) {
                View childView = RecyclerViewClickListenerImpl.this.recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && RecyclerViewClickListenerImpl.this.mListener != null) {
                    RecyclerViewClickListenerImpl.this.mListener.onItemLongClick(childView, RecyclerViewClickListenerImpl.this.recyclerView.getChildLayoutPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        //把事件交给GestureDetector处理
        if (mGestureDetector.onTouchEvent(e)) {
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
