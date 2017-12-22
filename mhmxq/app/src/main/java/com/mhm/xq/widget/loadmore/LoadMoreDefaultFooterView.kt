package com.mhm.xq.widget.loadmore

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.mhm.xq.R

class LoadMoreDefaultFooterView : RelativeLayout, LoadMoreUIHandler {

    private var mTextView: TextView? = null

    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        setupViews()
    }

    fun getTextView(): TextView? {
        return mTextView
    }

    fun setTextView(textView: TextView) {
        mTextView = textView
    }

    private fun setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.common_view_load_more_default_footer, this)
        mTextView = findViewById<View>(R.id.tvFooter) as TextView
    }

    override fun onLoading(container: LoadMoreContainer) {
        setVisibility(View.VISIBLE)
        mTextView!!.setText(R.string.common_load_more_loading)
    }

    override fun onLoadFinish(container: LoadMoreContainer, empty: Boolean, hasMore: Boolean) {
        if (!hasMore) {
            setVisibility(View.VISIBLE)
            if (empty) {
                mTextView!!.setText(R.string.common_load_more_loaded_empty)
            } else {
                mTextView!!.setText(R.string.common_load_more_loaded_no_more)
            }
        } else {
            setVisibility(View.INVISIBLE)
        }
    }

    override fun onWaitToLoadMore(container: LoadMoreContainer) {
        setVisibility(View.VISIBLE)
        mTextView!!.setText(R.string.common_load_more_click_to_load_more)
    }

    override fun onLoadError(container: LoadMoreContainer, errorCode: Int, errorMessage: String) {
        setVisibility(View.VISIBLE)
        mTextView!!.setText(R.string.common_load_more_error)
    }
}