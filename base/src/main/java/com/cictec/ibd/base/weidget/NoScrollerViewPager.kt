package com.cictec.ibd.base.weidget

import android.content.Context

import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by Administrator on 2015/10/14.
 */
class NoScrollerViewPager : ViewPager {
    private var noScroll = true

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        /* return false;//super.onTouchEvent(arg0); */
        return if (noScroll) {
            false
        } else {
            super.onTouchEvent(arg0)
        }
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll) {
            false
        } else {
            super.onInterceptTouchEvent(arg0)
        }
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }

}
