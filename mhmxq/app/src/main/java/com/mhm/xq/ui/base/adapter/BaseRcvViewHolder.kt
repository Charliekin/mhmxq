package com.mhm.xq.ui.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseRcvViewHolder<T> : RecyclerView.ViewHolder {

    protected var sPosition: Int? = 0
    protected var sItem: T? = null

    constructor(layoutRes: Int, parent: ViewGroup?) :
            super(LayoutInflater.from(parent!!.context).inflate(layoutRes, parent, false)) {
        onBindViews()
        onSetViews()
    }

    constructor(itemView: View, parent: ViewGroup?) : super(itemView) {
        onBindViews()
        onSetViews()
    }

    public fun updateViews(model: T, position: Int) {
        this.sPosition = position
        this.sItem = model
        onUpdateViews(model, position)
    }

    abstract public fun onBindViews()
    abstract public fun onSetViews()
    abstract public fun onUpdateViews(model: T, position: Int)

}