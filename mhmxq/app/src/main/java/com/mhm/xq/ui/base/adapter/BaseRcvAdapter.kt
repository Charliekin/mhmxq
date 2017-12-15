package com.mhm.xq.ui.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.mhm.xq.utils.ListUtils

abstract class BaseRcvAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mDataList: ArrayList<T>? = ArrayList()
    private var mNotifyAllWhenChange: Boolean = true
    public var mOnItemClickListener: OnItemClickListener? = null
    public var mOnItemLongClickListener: OnItemLongClickListener<T>? = null

    public fun isNotifyAllWhenChange(): Boolean {
        return mNotifyAllWhenChange
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>) {
        mOnItemLongClickListener = onItemLongClickListener
    }

    public fun setNotifyAllWhenChange(notifyAllWhenChange: Boolean) {
        mNotifyAllWhenChange = notifyAllWhenChange
    }

    public fun getDataList(): List<T> {
        return mDataList!!
    }

    public fun getItem(position: Int): T {
        return mDataList!![position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as BaseRcvViewHolder<T>).updateViews(getItem(position), position)
    }

    override fun getItemCount(): Int = mDataList!!.size

    fun changeDataList(dataList: List<T>) {
        mDataList!!.clear()
        mDataList!!.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addDataList(position: Int, dataList: List<T>) {
        mDataList!!.addAll(position, dataList)
        if (isNotifyAllWhenChange()) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(position, dataList.size)
        }
    }

    fun addDataList(dataList: List<T>) {
        mDataList!!.addAll(dataList)
        if (isNotifyAllWhenChange()) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(mDataList!!.size - dataList.size, dataList.size)
        }
    }

    fun addData(data: T) {
        mDataList!!.add(data)
        if (isNotifyAllWhenChange()) {
            notifyDataSetChanged()
        } else {
            notifyItemInserted(mDataList!!.size - 1)
        }
    }

    fun addData(location: Int, data: T) {
        mDataList!!.add(location, data)
        if (isNotifyAllWhenChange()) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeInserted(location, 1)
        }
    }

    fun removeData(data: T) {
        mDataList!!.remove(data)
        notifyDataSetChanged()
    }

    fun getFirstData(): T? {
        return if (mDataList!!.size == 0) {
            null
        } else mDataList!![0]
    }

    fun getLastData(): T? {
        return if (mDataList!!.size == 0) {
            null
        } else mDataList!![mDataList!!.size - 1]
    }

    fun clear() {
        mDataList!!.clear()
        notifyDataSetChanged()
    }

    fun dropWhile(callback: ListUtils.OnPredicateCallback<T>) {
        val removeIndex = ListUtils.dropWhile<T>(mDataList!!, callback)
        if (removeIndex != -1) {
            if (isNotifyAllWhenChange()) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeRemoved(0, removeIndex + 1)
            }
        }
    }

    fun dropLastWhile(callback: ListUtils.OnPredicateCallback<T>) {
        val removeIndex = ListUtils.dropLastWhile<T>(mDataList!!, callback)
        if (removeIndex != -1) {
            if (isNotifyAllWhenChange()) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeRemoved(removeIndex, mDataList!!.size - removeIndex)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    interface OnItemLongClickListener<T> {
        fun onItemLongClick(v: View, model: T): Boolean
    }
}