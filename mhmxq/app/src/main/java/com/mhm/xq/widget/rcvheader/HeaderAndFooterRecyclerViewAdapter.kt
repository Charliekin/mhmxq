package com.mhm.xq.widget.rcvheader

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.mhm.xq.utils.ThreadUtil

class HeaderAndFooterRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    val BASE_TYPE_HEADER_FOOTER = Integer.MIN_VALUE

    private var mIntCurrentType = BASE_TYPE_HEADER_FOOTER


    private val mSpaAllView = SparseArray<View>()
    private val mSpaHeader = SparseArray<View>()
    private val mSpaFooter = SparseArray<View>()

    private var mInnerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    private val mOdsInnerAdapter = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            super.onChanged()
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            notifyItemRangeChanged(positionStart + getHeaderViewsCount(), itemCount)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            notifyItemRangeInserted(positionStart + getHeaderViewsCount(), itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            notifyItemRangeRemoved(positionStart + getHeaderViewsCount(), itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            val headerViewsCountCount = getHeaderViewsCount()
            notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount)
        }
    }


    constructor() {
    }

    constructor(innerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        setInnerAdapter(innerAdapter)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        super.onViewAttachedToWindow(holder)
        if (mInnerAdapter != null) {
            mInnerAdapter!!.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
        super.onViewDetachedFromWindow(holder)
        if (mInnerAdapter != null) {
            mInnerAdapter!!.onViewDetachedFromWindow(holder)
        }
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder?): Boolean {
        if (mInnerAdapter != null) {
            mInnerAdapter!!.onFailedToRecycleView(holder)
        }
        return super.onFailedToRecycleView(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType >= 0) {
            mInnerAdapter!!.onCreateViewHolder(parent, viewType)
        } else {
            ViewHolder(mSpaAllView.get(viewType))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val headerViewsCountCount = getHeaderViewsCount()
        if (position >= headerViewsCountCount && position < headerViewsCountCount + getInnerAdapterItemCount()) {
            mInnerAdapter!!.onBindViewHolder(holder, position - headerViewsCountCount)
        } else {
            val layoutParams = holder.itemView.layoutParams
            if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                layoutParams.isFullSpan = true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val innerCount = getInnerAdapterItemCount()
        val headerViewsCount = getHeaderViewsCount()
        if (position < headerViewsCount) {
            return mSpaHeader.keyAt(position)
        } else if (position >= headerViewsCount && position < headerViewsCount + innerCount) {
            val innerItemViewType = mInnerAdapter!!.getItemViewType(position - headerViewsCount)
            if (innerItemViewType < 0) {
                throw IllegalArgumentException("your adapter's return value of getViewTypeCount() must >= 0")
            }
            return innerItemViewType
        } else {
            return mSpaFooter.keyAt(position - innerCount - headerViewsCount)
        }
    }

    override fun getItemCount(): Int {
        return getHeaderViewsCount() + getFooterViewsCount() + getInnerAdapterItemCount()
    }

    private fun getInnerAdapterItemCount(): Int {
        return if (mInnerAdapter == null) {
            0
        } else {
            mInnerAdapter!!.itemCount
        }
    }

    fun getHeaderViewsCount(): Int {
        return mSpaHeader.size()
    }

    fun getFooterViewsCount(): Int {
        return mSpaFooter.size()
    }

    fun isAddHeaderOrFooterView(view: View): Boolean {
        return if (mSpaHeader.indexOfValue(view) != -1 || mSpaFooter.indexOfValue(view) != -1) {
            true
        } else false
    }

    fun addHeaderOrFooterView(view: View?): Int {
        if (!ThreadUtil.isOnMainThread()) {
            throw IllegalArgumentException("You must call this method on the main thread")
        }
        if (view == null) {
            throw NullPointerException()
        }
        if (mSpaHeader.indexOfValue(view) != -1 || mSpaFooter.indexOfValue(view) != -1) {
            throw IllegalArgumentException("already add this view")
        }
        val index = mSpaAllView.indexOfValue(view)
        if (index != -1) {
            return mSpaAllView.keyAt(index)
        } else {
            mIntCurrentType = mIntCurrentType + 1
            val newKey = mIntCurrentType
            mSpaAllView.put(newKey, view)
            return newKey
        }
    }

    fun addHeaderView(header: View) {
        val viewType = addHeaderOrFooterView(header)
        mSpaHeader.put(viewType, header)
        notifyDataSetChanged()
    }

    fun addFooterView(footer: View) {
        val viewType = addHeaderOrFooterView(footer)
        mSpaFooter.put(viewType, footer)
        notifyDataSetChanged()
    }

    fun removeHeaderView(view: View) {
        val index = mSpaHeader.indexOfValue(view)
        if (index != -1) {
            mSpaHeader.removeAt(index)
            notifyDataSetChanged()
        }
    }

    fun removeFooterView(view: View) {
        val index = mSpaFooter.indexOfValue(view)
        if (index != -1) {
            mSpaFooter.removeAt(index)
            notifyDataSetChanged()
        }
    }

    fun removeHeaderView(index: Int) {
        mSpaHeader.removeAt(index)
        notifyDataSetChanged()
    }

    fun removeFooterView(index: Int) {
        mSpaFooter.removeAt(index)
        notifyDataSetChanged()
    }

    fun getHeaderView(index: Int): View {
        return mSpaHeader.valueAt(index)
    }

    fun getFooterView(index: Int): View {
        return mSpaFooter.valueAt(index)
    }

    fun clearAllHeaderView() {
        mSpaHeader.clear()
        notifyDataSetChanged()
    }

    fun clearAllFooterView() {
        mSpaFooter.clear()
        notifyDataSetChanged()
    }

    fun isHeaderView(position: Int): Boolean {
        return position >= 0 && position < getHeaderViewsCount()
    }

    fun isFooterView(position: Int): Boolean {
        return position >= 0 && position > getHeaderViewsCount() + getInnerAdapterItemCount()
    }

    fun isItemView(position: Int): Boolean {
        val innerCount = getInnerAdapterItemCount()
        val headerViewsCount = getHeaderViewsCount()
        return position >= 0 && position >= headerViewsCount && position < headerViewsCount + innerCount
    }

    fun getInnerAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return mInnerAdapter
    }

    fun setInnerAdapter(innerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        if (mInnerAdapter != null) {
            notifyItemRangeRemoved(getHeaderViewsCount(), mInnerAdapter!!.itemCount)
            mInnerAdapter!!.unregisterAdapterDataObserver(mOdsInnerAdapter)
        }
        mInnerAdapter = innerAdapter
        if (mInnerAdapter != null) {
            mInnerAdapter!!.registerAdapterDataObserver(mOdsInnerAdapter)
            notifyItemRangeInserted(getHeaderViewsCount(), mInnerAdapter!!.itemCount)
        }
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}