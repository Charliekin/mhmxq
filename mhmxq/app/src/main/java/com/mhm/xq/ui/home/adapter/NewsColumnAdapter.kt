package com.mhm.xq.ui.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mhm.xq.R
import com.mhm.xq.entity.New
import com.mhm.xq.ui.base.adapter.BaseRcvAdapter
import com.mhm.xq.ui.base.adapter.BaseRcvViewHolder

class NewsColumnAdapter : BaseRcvAdapter<New>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(parent)

    class ViewHolder : BaseRcvViewHolder<New> {
        constructor(parent: ViewGroup?) : super(R.layout.my_item_bottom_dialog, parent)

        override fun onBindViews() {

        }

        override fun onSetViews() {
        }

        override fun onUpdateViews(model: New, position: Int) {
        }

    }

}