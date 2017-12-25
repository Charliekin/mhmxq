package com.mhm.xq.widget.rcvheader

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class RecyclerViewWithHeaderAndFooter : RecyclerView {
    private var mWrapAdapter: HeaderAndFooterRecyclerViewAdapter? = null

    fun getWrapAdapter(): HeaderAndFooterRecyclerViewAdapter? {
        return mWrapAdapter
    }

    fun getInnerAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return mWrapAdapter!!.getInnerAdapter()
    }

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        mWrapAdapter = HeaderAndFooterRecyclerViewAdapter()
        super.setAdapter(mWrapAdapter)
    }

    fun isAddHeaderOrFooterView(view: View): Boolean {
        return mWrapAdapter!!.isAddHeaderOrFooterView(view)
    }

    fun addHeaderView(view: View) {
        mWrapAdapter!!.addHeaderView(view)
    }

    fun removeHeaderView(view: View) {
        mWrapAdapter!!.removeHeaderView(view)
    }

    fun addFooterView(view: View) {
        mWrapAdapter!!.addFooterView(view)
    }

    fun removeFooterView(view: View) {
        mWrapAdapter!!.removeFooterView(view)
    }

    fun getHeaderViewCount(): Int {
        return mWrapAdapter!!.getHeaderViewsCount()
    }

    fun getFooterViewCount(): Int {
        return mWrapAdapter!!.getFooterViewsCount()
    }

    fun removeHeaderView(index: Int) {
        mWrapAdapter!!.removeHeaderView(index)
    }

    fun removeFooterView(index: Int) {
        mWrapAdapter!!.removeFooterView(index)
    }

    fun getHeaderView(index: Int): View {
        return mWrapAdapter!!.getHeaderView(index)
    }

    fun getFooterView(index: Int): View {
        return mWrapAdapter!!.getFooterView(index)
    }

    fun clearAllHeaderView() {
        mWrapAdapter!!.clearAllHeaderView()
    }

    fun clearAllFooterView() {
        mWrapAdapter!!.clearAllFooterView()
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        mWrapAdapter!!.setInnerAdapter(adapter)
    }
}