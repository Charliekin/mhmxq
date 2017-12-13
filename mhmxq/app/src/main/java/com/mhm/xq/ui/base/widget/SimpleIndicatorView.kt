package com.mhm.xq.ui.base.widget

import android.content.Context
import android.database.DataSetObserver
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.mhm.xq.R

class SimpleIndicatorView : LinearLayout {
    private val DEF_INDICATOR_MARGIN = 16

    private var mSelectedDrawable: Drawable? = null
    private var mNormalDrawable: Drawable? = null

    private var mIndicatorMargin: Int = 0

    private var mViewpager: ViewPager? = null
    private var mLastPosition = -1


    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        gravity = Gravity.CENTER
        initAttrs(attrs)
    }

    fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleIndicatorView)
        if (typedArray != null) {
            mSelectedDrawable = typedArray.getDrawable(R.styleable.SimpleIndicatorView_indicatorSelected)
            mNormalDrawable = typedArray.getDrawable(R.styleable.SimpleIndicatorView_indicatorNormal)
            mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.SimpleIndicatorView_indicatorMargin, DEF_INDICATOR_MARGIN)
            typedArray.recycle()
        }
    }

    fun setViewPager(viewPager: ViewPager) {
        if (mViewpager === viewPager) return
        mViewpager = viewPager
        if (mViewpager != null && mViewpager!!.adapter != null) {
            mLastPosition = -1
            createIndicators()
            mViewpager!!.removeOnPageChangeListener(mInternalPageChangeListener)
            mViewpager!!.addOnPageChangeListener(mInternalPageChangeListener)
            mInternalPageChangeListener.onPageSelected(mViewpager!!.currentItem)

            mViewpager!!.adapter!!.registerDataSetObserver(getDataSetObserver())
        }
    }

    private fun setIndicatorAppear(view: View?, focus: Boolean) {
        if (view == null) {
            return
        }
        view.setBackgroundDrawable(if (focus) mSelectedDrawable else mNormalDrawable)
    }

    private val mInternalPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {

            if (mViewpager!!.adapter == null || mViewpager!!.adapter!!.count <= 0) {
                return
            }

            if (mLastPosition >= 0 && getChildAt(mLastPosition) != null) {
                setIndicatorAppear(getChildAt(mLastPosition), false)
            }

            val selectedIndicator = getChildAt(position)
            if (selectedIndicator != null) {
                setIndicatorAppear(selectedIndicator, true)
            }
            mLastPosition = position
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    fun getDataSetObserver(): DataSetObserver {
        return mInternalDataSetObserver
    }

    private val mInternalDataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            if (mViewpager == null) {
                return
            }

            val newCount = mViewpager!!.adapter!!.count
            val currentCount = childCount

            if (newCount == currentCount) {
                mInternalPageChangeListener.onPageSelected(mViewpager!!.currentItem)
                return
            } else if (mLastPosition < newCount) {
                mLastPosition = mViewpager!!.currentItem
            } else {
                mLastPosition = -1
            }

            createIndicators()
        }
    }


    private fun createIndicators() {
        removeAllViews()
        val count = mViewpager!!.adapter!!.count
        if (count <= 0) {
            return
        }
        val currentItem = mViewpager!!.currentItem

        for (i in 0 until count) {
            val view = ImageView(context)
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.leftMargin = mIndicatorMargin / 2
            params.rightMargin = mIndicatorMargin / 2
            view.layoutParams = params
            setIndicatorAppear(view, i == currentItem)
            addView(view)
        }
    }
}