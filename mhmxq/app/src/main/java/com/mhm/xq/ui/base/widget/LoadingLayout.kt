package com.mhm.xq.ui.base.widget

import android.content.Context
import android.support.annotation.Px
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.mhm.xq.R

class LoadingLayout : FrameLayout {

    val EMPTY_VIEW = 0
    val ERROR_VIEW = 1
    val LOADING_VIEW = 2
    val CONTENT_VIEW = 3


    private var mOnRetryClickListener: View.OnClickListener? = null

    fun setOnRetryClickListener(onRetryClickListener: View.OnClickListener) {
        mOnRetryClickListener = onRetryClickListener
    }


    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            val attrsTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout)
            if (attrsTypedArray != null) {
                val resLoadingView = attrsTypedArray!!.getResourceId(R.styleable.LoadingLayout_loading_view,
                        R.layout.common_view_loading)
                val resErrorView = attrsTypedArray!!.getResourceId(R.styleable.LoadingLayout_error_view,
                        R.layout.common_view_error)
                val resEmptyView = attrsTypedArray!!.getResourceId(R.styleable.LoadingLayout_empty_view,
                        R.layout.common_view_empty)
                val inflater = LayoutInflater.from(context)
                inflater.inflate(resEmptyView, this, true)
                inflater.inflate(resErrorView, this, true)
                inflater.inflate(resLoadingView, this, true)
                attrsTypedArray!!.recycle()
            }
        }
    }

    protected override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount < 4) {
            throw IllegalStateException("content view can not be null")
        }
        for (i in 0 until childCount - 1) {
            getChildAt(i).visibility = View.GONE
        }

        val viewEmptyRetry = findViewById<View>(R.id.viewEmptyRetry)
        if (viewEmptyRetry != null) {
            viewEmptyRetry!!.setOnClickListener(View.OnClickListener { v ->
                if (mOnRetryClickListener != null) {
                    mOnRetryClickListener!!.onClick(v)
                }
            })
        }

        val viewErrorRetry = findViewById<View>(R.id.viewErrorRetry)
        if (viewErrorRetry != null) {
            viewErrorRetry!!.setOnClickListener(View.OnClickListener { v ->
                if (mOnRetryClickListener != null) {
                    mOnRetryClickListener!!.onClick(v)
                }
            })
        }

    }

    //<editor-fold desc="show view">
    fun showEmpty(animate: Boolean) {
        showIndex(EMPTY_VIEW, animate)
    }

    fun showEmpty() {
        showEmpty(false)
    }

    fun showError(animate: Boolean) {
        showIndex(ERROR_VIEW, animate)
    }

    fun showError() {
        showError(false)
    }

    fun showLoading(animate: Boolean) {
        showIndex(LOADING_VIEW, animate)
    }

    fun showLoading() {
        showLoading(false)
    }

    fun showContent(animate: Boolean) {
        showIndex(CONTENT_VIEW, animate)
    }

    fun showContent() {
        showContent(false)
    }

    private fun showIndex(index: Int, animate: Boolean) {
        for (i in 0 until this.childCount) {
            val child = this.getChildAt(i)
            if (i == index) {
                if (child.visibility == View.GONE) {
                    if (animate) {
                        child.startAnimation(AnimationUtils.loadAnimation(
                                context, android.R.anim.fade_in))
                    } else {
                        child.clearAnimation()
                    }
                    child.visibility = View.VISIBLE
                }

            } else {
                if (child.visibility == View.VISIBLE) {
                    if (animate) {
                        child.startAnimation(AnimationUtils.loadAnimation(
                                context, android.R.anim.fade_out))
                    } else {
                        child.clearAnimation()
                    }
                    child.visibility = View.GONE
                }
            }
        }
    }
    //</editor-fold>


    //<editor-fold desc="set view">
    fun setView(index: Int, view: View) {
        if (index > CONTENT_VIEW || index < 0) {
            throw IllegalArgumentException()
        }
        val currentType = getViewType()
        removeViewAt(index)
        addView(view, index)
        showIndex(index, false)
    }

    fun setLoadingView(view: View) {
        setView(LOADING_VIEW, view)
    }

    fun setEmptyView(view: View) {
        setView(EMPTY_VIEW, view)
    }

    fun setErrorView(view: View) {
        setView(ERROR_VIEW, view)
    }

    fun setContentView(view: View) {
        setView(CONTENT_VIEW, view)
    }
    //</editor-fold>

    fun setStateViewMargin(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int) {
        for (view in arrayOf<View>(getChildAt(EMPTY_VIEW), getChildAt(ERROR_VIEW), getChildAt(LOADING_VIEW))) {
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.leftMargin = left
            params.topMargin = top
            params.rightMargin = right
            params.bottomMargin = bottom
            view.layoutParams = params
        }
    }

    fun getViewType(): Int {
        for (i in 0 until this.childCount) {
            val child = this.getChildAt(i)
            if (child.visibility == View.VISIBLE) {
                return i
            }
        }
        throw NullPointerException()
    }

}