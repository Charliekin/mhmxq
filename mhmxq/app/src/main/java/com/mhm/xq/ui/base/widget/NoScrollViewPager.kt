package com.mhm.xq.ui.base.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NoScrollViewPager : ViewPager {
    private var mIsScroll: Boolean = true

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

    }

    public fun setIsScroll(isScroll: Boolean) {
        mIsScroll = isScroll
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if (mIsScroll) {
            false
        } else {
            super.onTouchEvent(ev)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (mIsScroll) {
            false
        } else {
            return super.onInterceptTouchEvent(ev)
        }
    }
}